package core.base.serviceframe;

public interface IServiceFrame {
    void initPath();
    void initLibData();
    void initXmlData();
    void initLogger();

    void initOpenServerTime();

    void initServers();

    void intiProtocol();
    void updateXml();

    void initService();
    void updateService();
}
