package cn.easybuy.dao.news;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.News;
import cn.easybuy.params.NewsParams;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsDaoImpl extends BaseDaoImpl implements NewsDao{

    public NewsDaoImpl(Connection connection) {
        super(connection);
    }

    public News tableToClass(ResultSet rs) throws Exception {
        News news=new News();
        news.setId(rs.getInt("id"));
        news.setTitle(rs.getString("title"));
        news.setContent(rs.getString("content"));
        news.setCreateTime(rs.getDate("createTime"));
        return news;
    }

    //查询所有的新闻   分页时用到
    @Override
    public List<News> queryAllNews() throws Exception {
        List<News>newsList=new ArrayList<News>();//用于接收查询出来的数据  然后进行返回
        StringBuffer sql=new StringBuffer("select id,title,content,createTime FROM easybuy_news ");
        sql.append(" limit 0,5");
        ResultSet rs=this.executeQuery(sql.toString(),null);
        while(rs.next()){
            News news=tableToClass(rs);
            newsList.add(news);
        }
        return newsList;
    }
    // 保存新闻
    public void save(News news) throws Exception {
        String sql = " INSERT into easybuy_news(title,content,createTime) values( ?, ?, ?) ";
        Object[] params = new Object[] { news.getTitle(), news.getContent(),new Date() };
        this.execuptUpdate(sql, params);
    }
    // 更新新闻
    public void update(News news) throws Exception {
        String sql = " update easybuy_news set title=?,content=? where id=? ";
        Object[] params = new Object[] {news.getTitle(), news.getContent(),news.getId() };
        this.execuptUpdate(sql, params);
    }

    public void deleteById(Integer id) throws Exception {
        String sql = " delete from easybuy_news where id = ? ";
        Object params[] = new Object[] { id };
        this.execuptUpdate(sql.toString(), params);
    }
    //根据特性ID查询指定新闻
    public News getNewsById(Integer id) {
        String sql = " select * from easybuy_news where id = ? ";
        ResultSet resultSet = null;
        News news = null;
        try {
            Object params[] = new Object[] { id };
            resultSet = this.executeQuery(sql, params);
            while (resultSet.next()) {
                news = tableToClass(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet);
            this.closeResource();
            return news;
        }
    }

    public List<News> getAllNews(Pager pager) {
        List<News> newsList = null;
        String sql = " select id,title,content,createTime FROM easybuy_news ";
        ResultSet resultSet = this.executeQuery(sql, new Object[] {});
        try {
            newsList=new ArrayList<News>();
            while (resultSet.next()) {
                News news = this.tableToClass(resultSet);
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource(resultSet);
            this.closeResource();
            return newsList;
        }
    }

    @Override
    public List<News> queryNewsList(NewsParams params) {
        List<Object> paramsList=new ArrayList<Object>();
        List<News> newsList=new ArrayList<News>();
        StringBuffer sql=new StringBuffer(" select id,title,content,createTime FROM easybuy_news where 1=1 ");
        if(EmptyUtils.isNotEmpty(params.getTitle())){
            sql.append(" and title like ? ");
            paramsList.add(params.getTitle());
        }
        if(EmptyUtils.isNotEmpty(params.getSort())){
            sql.append(" order by " + params.getSort()+" ");
        }
        if(params.isPage()){
            sql.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
        }

        ResultSet resultSet = this.executeQuery(sql.toString(),paramsList.toArray());
        try {
            while (resultSet.next()) {
                News news = this.tableToClass(resultSet);
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource(resultSet);
            this.closeResource();
        }

        return newsList;
    }

    @Override
    public Integer queryNewsCount(NewsParams params) {
        List<Object> paramsList=new ArrayList<Object>();
        Integer count = 0;
        StringBuffer sql=new StringBuffer(" select count(*) as count FROM easybuy_news where 1=1 ");
        if(EmptyUtils.isNotEmpty(params.getTitle())){
            sql.append(" and title like ? ");
            paramsList.add(params.getTitle());
        }
        ResultSet resultSet = this.executeQuery(sql.toString(),paramsList.toArray());
        try {
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource(resultSet);
            this.closeResource();
        }
        return count;
    }

}
