package com.presentation.demo.business.parser;

import com.presentation.demo.helpers.MapEntryImpl;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.List;

public interface ParserHTML {

    public void connect(String target);

    public List<MapEntryImpl<String,String>> select(List<String> targetInfo);

}
