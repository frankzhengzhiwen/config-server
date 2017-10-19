package micro.orm.druid;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 连接池属性
 * 
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月6日
 */
@ConfigurationProperties(prefix = "druid")
public class DruidProperties {
    private String url;
    private String username;
    private String password;
    private String driverClass;

    private int     initialSize;
    private int     minIdle;
    private int     maxActive;
    private int 	maxWait;
    private int 	timeBetweenEvictionRunsMillis;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private int		minEvictableIdleTimeMillis;
    private boolean testWhileIdle;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

	/**
	 * @return property value of maxWait
	 */
	public int getMaxWait() {
		return maxWait;
	}

	/**
	 * @param maxWait value to be assigned to property maxWait
	 */
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	/**
	 * @return property value of timeBetweenEvictionRunsMillis
	 */
	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	/**
	 * @param timeBetweenEvictionRunsMillis value to be assigned to property timeBetweenEvictionRunsMillis
	 */
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	/**
	 * @return property value of testOnReturn
	 */
	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	/**
	 * @param testOnReturn value to be assigned to property testOnReturn
	 */
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	/**
	 * @return property value of minEvictableIdleTimeMillis
	 */
	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	/**
	 * @param minEvictableIdleTimeMillis value to be assigned to property minEvictableIdleTimeMillis
	 */
	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	/**
	 * @return property value of testWhileIdle
	 */
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	/**
	 * @param testWhileIdle value to be assigned to property testWhileIdle
	 */
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
}
