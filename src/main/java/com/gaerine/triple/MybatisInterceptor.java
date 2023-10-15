package com.gaerine.triple;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;


@Intercepts({
        @Signature(type = Executor.class,method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];

        System.out.println("mappedStatement = " + mappedStatement.getSqlCommandType());

        try{
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                DataSourceRouting.setDbType("local");
            } else{
                DataSourceRouting.setDbType("cloude");
            }

            return invocation.proceed();

        } finally {
            DataSourceRouting.clearDbType();
        }

    }
}
