package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();


    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if(totalCount%size==0){
            totalPage=totalCount/size;
            if(totalPage==0)totalPage=1;
        }else{
            totalPage=totalCount/size+1;
        }
        if(page<=1)page=1;
        if(page>=totalPage)page=totalPage;

        this.page = page;
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){pages.add(0,page-i);}
            if(page+i<=totalPage){pages.add(page+i);}
        }
        //是否展示上一页下一页
        if(page==1){ showPrevious=false; }else{ showPrevious=true;}
        if(page==totalPage){showNext=false;}else{showNext=true;}
        //是否展示第一页/最后一页
        if(pages.contains(1)){showFirstPage=false;}else{showFirstPage=true;}
        if(pages.contains(totalPage)){showEndPage=false;}else{showEndPage=true;}

    }
}
