package worldwide.clm.clmwebsite.utils;

public class WhiteList {

    public static  String[] freeAccess(){
        return new String[]{
                "/clmWebsite/api/v1/admin/registration"
        };
    }

    public static String[] swagger() {
        return new String[]{
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }
}
