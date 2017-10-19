/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package micro.orm.component;

/**
 * Mybatis数据库实体基类
 *
 * @author <a href="mailto:frankzhiwen@163.com">郑智文(Frank Zheng)</a>
 * @version 0.0.1
 * @date 2017年6月6日
 */
public class BaseEntity {

    /**
     * 数据库主键，统一使用UUID（去除-）
     * 对应字段Varchar2(32)
     */
//    @Id
//    @Column(name = "ID")
    private String id;

//    /**
//     * 上次修改的操作日期
//     * 对应字段Timestamp
//     */
//    private Date lastModifiedTime;
//    
//    /**
//     * 上次修改的操作账户
//     * 对应字段Varchar2(32)
//     */
//    private String lastModifiedAccount;
//    
//    /**
//     * 版本号
//     * 对应字段Number(18)
//     */
//    private Long version;
//    
//    private List<String> orgs;

//    @Transient
    private Integer page = 1;

//    @Transient
    private Integer rows = 10;

	/**
	 * @return property value of id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id value to be assigned to property id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return property value of page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page value to be assigned to property page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return property value of rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows value to be assigned to property rows
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
