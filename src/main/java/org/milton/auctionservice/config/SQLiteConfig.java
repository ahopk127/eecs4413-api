package org.milton.auctionservice.dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.NoIdentityColumnSupport;
import org.hibernate.type.StandardBasicTypes;
import java.sql.Types;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super();
        regis
        // Register SQLite specific types
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.VARCHAR, "text");
        registerColumnType(Types.TIMESTAMP, "datetime");
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new NoIdentityColumnSupport();
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }

    @Override
    public boolean supportsInsertSelectIdentity() {
        return false;
    }

    @Override
    public boolean supportsCommentOn() {
        return false;
    }

    @Override
    public boolean supportsLobValueChange() {
        return false;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        throw new UnsupportedOperationException("SQLite does not support identity select string");
    }

    @Override
    public String getIdentityColumnString(int type) {
        return "integer primary key autoincrement";
    }
}
