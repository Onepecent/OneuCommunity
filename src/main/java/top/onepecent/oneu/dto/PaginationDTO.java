package top.onepecent.oneu.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO<T> {
    private List<T> data;
    private boolean ShowPrevious;
    private boolean ShowFirstPage;
    private boolean ShowNext;
    private boolean ShowEndPage;
    private Integer page;//currPage
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;

        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页按钮
        ShowPrevious = page != 1;
        //是否展示下一页按钮
        ShowNext = page != totalPage;

        //是否展示首页按钮
        ShowFirstPage = !pages.contains(1);
//        if (pages.contains(1)){ShowFirstPage = false;}---------------------------解释---------
//          else {ShowFirstPage = true;}

        //是否展示尾页按钮
        ShowEndPage = !pages.contains(totalPage);

    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isShowPrevious() {
        return ShowPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        ShowPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return ShowFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        ShowFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return ShowNext;
    }

    public void setShowNext(boolean showNext) {
        ShowNext = showNext;
    }

    public boolean isShowEndPage() {
        return ShowEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        ShowEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }


}
