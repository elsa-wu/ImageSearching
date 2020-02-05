package com.example.elsa.imagesearching.mvp.model;

import com.stx.xhb.androidx.entity.SimpleBannerInfo;

import java.io.Serializable;
import java.util.List;

public class ImageBean implements Serializable {
    private String title;
    private String id;
    private List<ImageDetailBean> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ImageDetailBean> getImages() {
        return images;
    }

    public void setImages(List<ImageDetailBean> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                " title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", images=" + images +
                '}';
    }

    public class ImageDetailBean extends SimpleBannerInfo implements Serializable {
        private String link;
        private String description;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "ImageDetailBean{" +
                    "link='" + link + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        @Override
        public Object getXBannerUrl() {
            return null;
        }
    }
}
