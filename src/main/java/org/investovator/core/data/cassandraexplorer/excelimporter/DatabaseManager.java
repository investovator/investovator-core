package org.investovator.core.data.cassandraexplorer.excelimporter;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * Created with IntelliJ IDEA.
 * User: hasala
 * Date: 8/14/13
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseManager {

    private final static Cluster cassandraCluster = HFactory.createCluster("Test_Cluster", new CassandraHostConfigurator("127.0.0.1:9160"));
    private final static Keyspace investovatorData = HFactory.createKeyspace("investovatorData", cassandraCluster);

    private final static StringSerializer ss = StringSerializer.get();

    private final static String HISTORYDATA = "HistoryData";

    public void saveHistoryData(HistoryData historyData) {
        Mutator<String> mutator = HFactory.createMutator(investovatorData, ss);
        mutator.addInsertion(historyData.getDate(), HISTORYDATA, HFactory.createStringColumn("high_price", Double.toString(historyData.getHighPrice())))
                .addInsertion(historyData.getDate(), HISTORYDATA, HFactory.createStringColumn("low_price", Double.toString(historyData.getLowPrice())))
                .addInsertion(historyData.getDate(), HISTORYDATA, HFactory.createStringColumn("closing_price", Double.toString(historyData.getClosingPrice())))
                .addInsertion(historyData.getDate(), HISTORYDATA, HFactory.createColumn("no_of_shares", Double.toString(historyData.getNoOfShares())))
                .addInsertion(historyData.getDate(), HISTORYDATA, HFactory.createColumn("no_of_trades", Double.toString(historyData.getNoOfTrades())))
                .addInsertion(historyData.getDate(), HISTORYDATA, HFactory.createColumn("turnover", Double.toString(historyData.getTurnover())));

        try {
            mutator.execute();
        }
        catch (HectorException he)  {
        System.out.println(he.getMessage());

    }

    }

}
