public class Deps {
    public static final String junitVersion = "5.9.1";
    public static final String junitGroup = "org.junit.jupiter";
    public static final String junitJupiter = junitGroup + ":junit-jupiter:" + junitVersion;
    public static final String junitApi = junitGroup  + ":junit-jupiter-api:" + junitVersion;
    public static final String junitParams = junitGroup + ":junit-jupiter-params:" + junitVersion;
    public static final String junitEngine = junitGroup + ":junit-jupiter-engine:" + junitVersion;

    public static final String slf4jVersion = "2.0.11";
    public static final String slf4jSimple = "org.slf4j:slf4j-simple:" + slf4jVersion;

    public static final String assertjVersion = "3.25.3";
    public static final String assertjGroup = "org.assertj";
    public static final String assertj = assertjGroup + ":assertj-core:" + assertjVersion;

    public static final String lombokVersion = "1.18.30";
    public static final String lombokGroup = "org.projectlombok";
    public static final String lombok = lombokGroup + ":lombok:" + lombokVersion;

    public static final String jacksonVersion = "2.16.1";
    public static final String jacksonGroup = "com.fasterxml.jackson.core";
    public static final String jacksonCore = jacksonGroup + ":jackson-core:" + jacksonVersion;
    public static final String jacksonDataBind = jacksonGroup + ":jackson-databind:" + jacksonVersion;

    public static final String hibernateVersion = "5.6.15.Final";
    public static final String hibernateGroup = "org.hibernate";
    public static final String hibernateCore = hibernateGroup + ":hibernate-core:" + hibernateVersion;
    public static final String hibernateEntity = hibernateGroup + ":hibernate-entitymanager:" + hibernateVersion;

    public static final String postgresqlVersion = "42.7.1";
    public static final String postgresqlGroup = "org.postgresql";
    public static final String postgresql = postgresqlGroup + ":postgresql:" + postgresqlVersion;
}
