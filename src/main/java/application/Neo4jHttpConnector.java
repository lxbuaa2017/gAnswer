package application;

import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.List;

public class Neo4jHttpConnector implements AutoCloseable
{
    private final static Driver driver = GraphDatabase.driver( "bolt://123.57.203.185:7687/db/data/", AuthTokens.basic( "neo4j", "kuda" ) );

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public static void test()
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
//                    Result result = tx.run( "CREATE (a:Greeting) " +
//                                    "SET a.message = $message " +
//                                    "RETURN a.message + ', from node ' + id(a)",
//                            parameters( "message", message ) );
                    Result result = tx.run("match (a)-[r]->(b) where a.name='姚明' and type(r)='妻子' return b");
                    return result.single().get( 0 ).asNode().values().toString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public static List<String> getAllNodeNames(){
        try ( Session session = driver.session() )
        {
            List<String> result = session.writeTransaction(new TransactionWork<List>()
            {
                @Override
                public List execute(Transaction tx )
                {
//                    Result result = tx.run( "CREATE (a:Greeting) " +
//                                    "SET a.message = $message " +
//                                    "RETURN a.message + ', from node ' + id(a)",
//                            parameters( "message", message ) );
                    Result result = tx.run("match (a) return a");
                    List<String> res = new ArrayList<>();
                    while (result.hasNext()){
                        res.add(result.next().get("a").asNode().get("name").asString());
                    }
                    return res;
                }
            } );
            return result;
        }
    }

    public static void main( String... args ) throws Exception
    {
//        test();
        System.out.println(getAllNodeNames());;
    }
}