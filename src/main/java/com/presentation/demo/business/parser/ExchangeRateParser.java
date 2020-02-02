package com.presentation.demo.business.parser;

import com.presentation.demo.helpers.MapEntryImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.presentation.demo.constants.Params.*;

public class ExchangeRateParser implements ParserHTML {

    private Logger exchangeRateLogger = LoggerFactory.getLogger(ExchangeRateParser.class);

    private Connection connection;

    private String useragent;

    private String referrerUrl;

    private String statusMessage = null;

    private Integer statusCode = -1;

    public ExchangeRateParser(){
    }

    public ExchangeRateParser(String referrerUrl, String useragent) {
        this.referrerUrl = referrerUrl;
        this.useragent = useragent;
    }

    @Override
    public void connect(String target){
        this.connection = Jsoup.connect(target).timeout(DEFAULT_TIMEOUT).followRedirects(FOLLOW_REDIRECTS);
        Connection.Response response = null;

        try{
            response = this.connection.execute();
        }
        catch (IOException io){
            exchangeRateLogger.info(io.getLocalizedMessage());
        }

        if(response != null){
            this.statusCode = response.statusCode();
            this.statusMessage = response.statusMessage();
        }

    }

    @Override
    public List<MapEntryImpl<String,String>> select(List<String> currencies){
        List<MapEntryImpl<String,String>> currencyPrice = new LinkedList<MapEntryImpl<String,String>>();
        List<String> singleCurrencyInfo = new LinkedList<String>();
        Document document = null;

        try{
            document = this.connection.get();
        }
        catch (IOException io){
            exchangeRateLogger.info(io.getLocalizedMessage());
            return currencyPrice;
        }

        Elements listExchangeRateTable = document.select(EXCHANGE_RATE_TAG);

        int currenciesToParse = currencies.size();
        int currenciesParsed = 0;

        for(Element singleCurrencyElementLineInfo: listExchangeRateTable.select("tr")){
            for(Element singleCurrencyElementInfo: singleCurrencyElementLineInfo.select("td")){
                singleCurrencyInfo.add(singleCurrencyElementInfo.text());
            }
            if (!singleCurrencyInfo.isEmpty() && currencies.contains(singleCurrencyInfo.get(1))){//country abbreviation in second "td" tag, rate in fifth "td" tag
                currencyPrice.add(new MapEntryImpl<String,String>(singleCurrencyInfo.get(1),singleCurrencyInfo.get(4)));
                currenciesParsed++;
                if (currenciesParsed == currenciesToParse){
                    break;
                }
            }
            singleCurrencyInfo.clear();
        }

        return currencyPrice;
    }

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getReferrerUrl() {
        return referrerUrl;
    }

    public void setReferrerUrl(String referrerUrl) {
        this.referrerUrl = referrerUrl;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
