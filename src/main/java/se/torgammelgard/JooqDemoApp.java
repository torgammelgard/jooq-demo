package se.torgammelgard;

import org.jooq.*;
import org.jooq.impl.DSL;
import se.torgammelgard.gencode.Tables;
import se.torgammelgard.gencode.tables.records.AuthorRecord;

import java.sql.Connection;
import java.sql.DriverManager;

import static se.torgammelgard.gencode.tables.Author.AUTHOR;

public class JooqDemoApp {
    private static final String userName = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/library";

    public static void main(String[] args) {
        final JooqDemoApp app = new JooqDemoApp();

        app.insertStuff();
        app.printStuff();
    }

    void insertStuff() {
        try (final Connection conn = DriverManager.getConnection(url, userName, password);
             final DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL)) {

            InsertValuesStep2<AuthorRecord, String, String> step = ctx
                    .insertInto(Tables.AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME);
            step.values("John", "Doe").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void printStuff() {
        try (final Connection conn = DriverManager.getConnection(url, userName, password);
             final DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL)) {

            Result<Record> result = ctx.select().from(Tables.AUTHOR).fetch();

            for (Record record : result) {
                final Integer id = record.getValue(AUTHOR.ID);
                final String firstName = record.getValue(AUTHOR.FIRST_NAME);
                final String lastName = record.getValue(AUTHOR.LAST_NAME);

                System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
