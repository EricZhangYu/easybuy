package cn.easybuy.dao.news;

import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;

import java.util.List;

public interface NewsDao {
    //查询首页中的新闻资讯
	public List<News> queryAllNews()throws  Exception;

    //保存新闻
    public void save(News news) throws Exception;

    //更新
    public void update(News news) throws Exception;

    //删除
    public void deleteById(Integer id) throws Exception;

    //根据指定id查找特定新闻
    public News getNewsById(Integer id)throws Exception;

    //查询资讯列表
    public List<News> queryNewsList(NewsParams params)throws Exception;

    //查询新闻数量
    public Integer queryNewsCount(NewsParams params)throws Exception;
}
