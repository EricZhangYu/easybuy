package cn.easybuy.service.news;

import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;
import cn.easybuy.utils.Pager;

import java.util.List;

public interface NewsService {
    public List<News> queryAllNews();
    // 保存新闻
    void saveNews(News news);
    // 根据id查询新闻
    News findNewsById(String parameter);
    // 查询所有的新闻
    List<News> getAllNews(Pager pager);
    // 删除新闻
    void deleteNews(String parameter);
    //查询新闻列表
    List<News> queryNewsList(NewsParams param);
    //查询数目
    Integer queryNewsCount(NewsParams param);
}
