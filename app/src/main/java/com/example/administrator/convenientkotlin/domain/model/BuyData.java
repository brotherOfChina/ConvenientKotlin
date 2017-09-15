package com.example.administrator.convenientkotlin.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class BuyData {

    /**
     * status : 0
     * msg :
     * data : {"list":[{"order_no":"2017091210124022600566","ctime":"2017-09-12 10:12:40","phone":"15903446555","items":[{"goods_id":"1803","goods_sn":"3000099","goods_name":"舒客红花清火牙膏120g","goods_num":"1"}]},{"order_no":"2017091210014587925268","ctime":"2017-09-12 10:01:45","phone":"13203542859","items":[{"goods_id":"290","goods_sn":"4000105","goods_name":"鱼香肉丝","goods_num":"1"},{"goods_id":"1029","goods_sn":"4000162","goods_name":"香辣虾","goods_num":"1"}]}]}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * order_no : 2017091210124022600566
             * ctime : 2017-09-12 10:12:40
             * phone : 15903446555
             * items : [{"goods_id":"1803","goods_sn":"3000099","goods_name":"舒客红花清火牙膏120g","goods_num":"1"}]
             */

            private String order_no;
            private String ctime;
            private String phone;
            private List<ItemsBean> items;

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * goods_id : 1803
                 * goods_sn : 3000099
                 * goods_name : 舒客红花清火牙膏120g
                 * goods_num : 1
                 */

                private String goods_id;
                private String goods_sn;
                private String goods_name;
                private String goods_num;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_sn() {
                    return goods_sn;
                }

                public void setGoods_sn(String goods_sn) {
                    this.goods_sn = goods_sn;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getGoods_num() {
                    return goods_num;
                }

                public void setGoods_num(String goods_num) {
                    this.goods_num = goods_num;
                }
            }
        }
    }
}
