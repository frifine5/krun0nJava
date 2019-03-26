package com.ca.service;

import org.springframework.stereotype.Service;

@Service
public class SysService {

    public String[] getSysKPair(){

        // demo
        String d = "044D4F588B76888D9D74785DB87A18FD346743602070DD21D824B8E4027452D68D90B6339CF86E48278ABD7B2FC249094FF31CD45C59EDCE0B21169AF505FA0ED8,5A325ABF49F554EF1508F11D4CA8513288600B144C01D7B9B7F8EB3425C2B41B";

        return d.split(",");
    }


}
