package cn.easybuy.service.news;

import cn.easybuy.dao.news.NewsDao;
import cn.easybuy.dao.news.NewsDaoImpl;
import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;
import cn.easybuy.utils.Constants;
import cn.easybuy.utils.DataSource;
import cn.easybuy.utils.Pager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsServiceImpl implements  NewsService {
    private Connection connection;
    private NewsDao newsDao;

    @Override
    public List<News> queryAllNews() {//获得所有的新闻
        List<News>newsList=new ArrayList<News>();
        try {
            connection = DataSource.openConnection();
            newsDao=new NewsDaoImpl(connection);
            newsList=newsDao.queryAllNews();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSource.closeConnection(connection);
        }
        return newsList;
    }

    public void deleteNews(String id) {// 删除新闻
        Connection connection=null;
        try {
            connection=DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsDao.deleteById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DataSource.closeConnection(connection);
        }
    }

    public News findNewsById(String id) {// 根据ID获取新闻
        News news = null;
        Connection connection=null;
        try {
            connection=DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            news = newsDao.getNewsById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return news;
    }

    public List<News> getAllNews(Pager pager) {// 获取当页新闻
        List<News> rtn = new ArrayList<News>();
        Connection connection=null;
        try {
            connection=DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(DataSource.openConnection());
            NewsParams params = new NewsParams();
            params.openPage((pager.getCurrentPage() - 1) * pager.getRowPerPage(),pager.getRowPerPage());
            rtn=newsDao.queryNewsList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            DataSource.closeConnection(connection);
        }
        return rtn;
    }

    public void saveNews(News news) {// 保存新闻
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsDao.save(news);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public void updateNews(News news) {// 更新留言
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsDao.update(news);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    public List<News> queryNewsPageList(NewsParams param) throws SQLException {
        List<News> newsList=new ArrayList<News>();
        Connection connection = null;
        NewsDao newsDao =null;
        try {
            connection = DataSource.openConnection();
            newsDao= new NewsDaoImpl(connection);
            newsList=newsDao.queryNewsList(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(connection.isClosed());
            DataSource.closeConnection(connection);
        }
        return newsList;
    }

    @Override//查询新闻列表
    public List<News> queryNewsList(NewsParams param) {
        List<News> newsList=new ArrayList<News>();
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            newsList=newsDao.queryNewsList(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return newsList;
    }

    @Override//查询新闻数目
    public Integer queryNewsCount(NewsParams param) {
        Connection connection = null;
        Integer count=0;
        try {
            connection = DataSource.openConnection();
            NewsDao newsDao = new NewsDaoImpl(connection);
            count=newsDao.queryNewsCount(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
            return count;
        }
    }
}
