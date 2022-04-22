package ua.tqs.tqscovid.config;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ua.tqs.tqscovid.adapter.APISportsAdapter;
import ua.tqs.tqscovid.adapter.IExternalAPIAdapter;
import ua.tqs.tqscovid.adapter.VACCOVIDAdapter;
import ua.tqs.tqscovid.utils.ConfigUtils;

@Configuration
@ComponentScan
public class AdapterConfig {
    
    @Bean
    public IExternalAPIAdapter getExternalApiAdapter() throws ParseException, IOException {
        String prop = ConfigUtils.getPropertyFromConfig("data.external.api");
        System.out.println(prop);
        if (prop.equals("VACCOVID")) {
            return new VACCOVIDAdapter();
        } else {
            return new APISportsAdapter();
        }
    }
}
