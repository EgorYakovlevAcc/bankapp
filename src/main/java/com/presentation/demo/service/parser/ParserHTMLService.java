package com.presentation.demo.service.parser;

import com.presentation.demo.pojo.MapEntryImpl;

import java.util.List;

public interface ParserHTMLService {

    public void connect(String target);

    public List<MapEntryImpl<String,String>> select(List<String> targetInfo);

}
