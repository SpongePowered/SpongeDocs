======================
Working with Databases
======================

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
    import java.sql.SQLException;

    private SqlService sql;
    public javax.sql.DataSource getDataSource(String jdbcUrl) throws SQLException {
        if (sql == null) {
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
        }
        return sql.getDataSource(jdbcUrl);
    }

    // Later on
    public void myMethodThatQueries() throws SQLException {
        Connection conn = getDataSource("jdbc:h2:imalittledatabaseshortandstout.db").getConnection();
        try {
            conn.prepareStatement("SELECT * FROM test_tbl").execute();
        } finally {
            conn.close();
        }

    }

The SQL service provides a pooled connection, so getting a connection from the returned DataSource is not expensive.
Therefore, we recommended not keeping connections around, and closing them soon after use instead, as shown in the
above example. (Proper resource management means you *do* have to close connections).

NoSQL
-----
Sponge does not currently provide any special abstraction over NoSQL databases (MongoDB etc). Plugins that wish to use
NoSQL databases must provide their own connectors.
