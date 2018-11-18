=========
Databases
=========

.. javadoc-import::
    java.lang.String
    org.spongepowered.api.service.sql.SqlService

SQL
---
Sponge provides a convenient abstraction for establishing JDBC database connections that handles the complexities of
establishing an efficient pooled connection from a JDBC URL.

While the SQL service supports any JDBC connector, the Forge implementation of Sponge only ships with the most common:

- MySQL
- Sqlite
- H2

.. warning::
    Because Sqlite has many limitations, its usage is strongly discouraged except in cases where legacy compatibility
    is required. H2 is our recommended file-backed database implementation.

Usage
~~~~~

A data source can be accessed through the plugin's service manager:

.. code-block:: java

    import org.spongepowered.api.Sponge;
    import org.spongepowered.api.service.sql.SqlService;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import javax.sql.DataSource;

    private SqlService sql;
    public DataSource getDataSource(String jdbcUrl) throws SQLException {
        if (sql == null) {
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
        }
        return sql.getDataSource(jdbcUrl);
    }

    // Later on
    public void myMethodThatQueries() throws SQLException {
        String uri = "jdbc:h2:imalittledatabaseshortandstout.db";
        String sql = "SELECT * FROM test_tbl";

        try (Connection conn = getDataSource(uri).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet results = stmt.executeQuery()) {

            while (results.next()) {
                // ...
            }

        }

    }

JDBC URLs should be obtained from :javadoc:`SqlService#getConnectionUrlFromAlias(String)`, with an alias that
matches one of the JDBC URL aliases listed in Sponge's global configuration (``config/sponge/global.conf``)
under the ``sponge.sql.aliases`` key.

The SQL service provides a pooled connection, so getting a connection from the returned ``DataSource``
is not expensive. Therefore, we recommended not keeping connections around, and closing them soon after use as shown
above.  Any ``PreparedStatement`` and ``ResultSet`` created should also be closed after use, with ``object.close()``
or, preferably, through a try-with-resources block.

NoSQL
-----
Sponge does not currently provide any special abstraction over NoSQL databases (MongoDB etc.). Plugins that wish to use
NoSQL databases must provide their own connectors.
