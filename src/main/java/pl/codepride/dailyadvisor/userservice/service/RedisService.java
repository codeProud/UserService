package pl.codepride.dailyadvisor.userservice.service;

public interface RedisService {

    public void saveKey(String key);

    public boolean checkIfKeyExists(String key);

    public void deleteKey(String key);

    public String getKey(String key);
}
