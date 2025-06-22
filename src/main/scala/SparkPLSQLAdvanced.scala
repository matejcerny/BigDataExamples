object SparkPLSQLAdvanced extends App with LocalSparkSession {

  /** Prepare delta tables and test data */
  sparkSession.sql(
    """
      |BEGIN
      |  CREATE TABLE raw_events (
      |      id INT
      |    , event_data STRING
      |    , event_timestamp TIMESTAMP
      |  ) USING delta;
      |
      |  CREATE TABLE processed_events AS SELECT * FROM raw_events;
      |
      |  INSERT INTO raw_events VALUES
      |      (1, '{"user": "a", "action": "login"}', to_timestamp('2025-06-22 19:00:00'))
      |    , (2, '{"user": "b", "action": "click"}', to_timestamp('2025-06-22 19:01:00'))
      |    , (3, '{"user": "a", "action": "logout"}', to_timestamp('2025-06-22 19:02:00'))
      |  ;
      |
      |  CREATE TABLE etl_metadata (
      |      source_name STRING
      |    , target_name STRING
      |    , last_processed_id INT
      |  ) USING delta;
      |
      |  INSERT INTO etl_metadata (source_name, target_name, last_processed_id)
      |    VALUES ('raw_events', 'processed_events', 0)
      |  ;
      |
      |  SELECT * FROM etl_metadata;
      |END
      |""".stripMargin
  ).show()

  /** Migrate data from raw to processed table and update the last id in metadata table */
  sparkSession.sql(
    """
      |BEGIN
      |  DECLARE new_max_id INT;
      |  DECLARE get_new_max_id_stmt STRING;
      |  DECLARE insert_stmt STRING;
      |  DECLARE update_stmt STRING;
      |
      |  FOR metadata AS
      |    (SELECT source_name, target_name, last_processed_id FROM etl_metadata)
      |  DO
      |    SET insert_stmt = 'INSERT INTO ' || metadata.target_name ||
      |                      '  SELECT * FROM ' || metadata.source_name ||
      |                      '  WHERE id > ?';
      |    EXECUTE IMMEDIATE insert_stmt USING metadata.last_processed_id;
      |
      |    SET get_new_max_id_stmt = 'SELECT MAX(id) FROM ' || metadata.source_name;
      |    EXECUTE IMMEDIATE get_new_max_id_stmt INTO new_max_id;
      |
      |    IF new_max_id IS NOT NULL THEN
      |      UPDATE etl_metadata
      |        SET last_processed_id = new_max_id
      |        WHERE source_name = metadata.source_name
      |          AND target_name = metadata.target_name
      |      ;
      |    END IF;
      |  END FOR;
      |
      |  SELECT * FROM etl_metadata;
      |END
      |""".stripMargin
  ).show()

  sparkSession.sql("DROP TABLE IF EXISTS raw_events")
  sparkSession.sql("DROP TABLE IF EXISTS processed_events")
  sparkSession.sql("DROP TABLE IF EXISTS etl_metadata")
}
