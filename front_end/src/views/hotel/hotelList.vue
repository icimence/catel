<template>
    <div class="hotelList">
        <a-layout>
            <div>
                <a-button @click="showDrawer" class="filterButton" type="primary">
                    <a-icon type="filter" />
                    搜索/排序
                </a-button>
                <a-drawer
                        :body-style="{ paddingBottom: '80px' }"
                        :visible="visible"
                        :width="420"
                        @close="onClose"
                        title="搜索/排序"
                >
                    <div class="global-search-wrapper">
                        <a-auto-complete
                                @search="handleSearch"
                                @select="onSelect"
                                class="global-search"
                                option-label-prop="title"
                                placeholder="请输入搜索关键词"
                                size="large"
                                style="width: 100%"
                        >
                            <a-input>
                                <a-button
                                        class="search-btn"
                                        size="large"
                                        slot="suffix"
                                        style="margin-right: -12px"
                                        type="primary"
                                >
                                    <a-icon type="search"/>
                                </a-button>
                            </a-input>
                        </a-auto-complete>
                    </div>
                    <div class="highAndLow">
                        <a-button @click="highRateFirst" type="primary">
                            <a-icon type="down"/>
                            按评分降序排列
                        </a-button>
                        <a-button @click="lowRateFirst" style="margin-left: 30px" type="primary">
                            <a-icon type="up"/>
                            按评分升序排列
                        </a-button>
                    </div>
                    <div class="highAndLow">
                        <a-button @click="highFirst" type="primary">
                            <a-icon type="down"/>
                            按销量降序排列
                        </a-button>
                        <a-button @click="lowFirst" style="margin-left: 30px" type="primary">
                            <a-icon type="up"/>
                            按销量升序排列
                        </a-button>
                    </div>
                </a-drawer>
            </div>
            <a-layout-content style="min-width: 800px">
                <a-spin :spinning="hotelListLoading">
                    <div class="card-wrapper">
                        <HotelCard :hotel="item" :key="item.index"
                                   @click.native="jumpToDetails(item.id)"
                                   v-for="item in hotelList.slice(8*(this.pageNo),8*(this.pageNo + 1))"></HotelCard>
                        <div :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3"
                             v-for="item in emptyBox">
                        </div>
                    </div>
                </a-spin>
                <a-pagination :defaultCurrent="1" :total="this.totalPage * 10" @change="pageChange" show-quick-jumper
                              style="padding-left: 50px"></a-pagination>
            </a-layout-content>
        </a-layout>

    </div>
</template>

<script>
    import HotelCard from './components/hotelCard';
    import {mapActions, mapGetters, mapMutations} from 'vuex';

    export default {
        name: 'home',
        components: {
            HotelCard,
        },

        data() {
            return {
                totalPage: 0,
                pageNo: 0,
                emptyBox: [{name: 'box1'}, {name: 'box2'}, {name: 'box3'}],
                visible: false,
            };
        },
        async mounted() {
            await this.getUserInfo();
            await this.getHotelList();
            this.totalPage = Math.ceil(this.hotelList.length / 8);
        },
        computed: {
            ...mapGetters([
                'hotelList',
                'hotelListLoading',
            ]),
        },
        methods: {
            ...mapMutations([
                'set_hotelListParams',
                'set_hotelListLoading',
            ]),
            ...mapActions([
                'getHotelList',
                "getUserInfo",
                'searchToUpdateHotelList',
                "getHotelHot",
                "getHotelCold"
            ]),

            pageChange(page, pageSize) {
                this.pageNo = page - 1;
                const data = {
                    pageNo: page - 1,
                };
                this.set_hotelListParams(data);
                this.set_hotelListLoading(true);
                this.getHotelList();
            },
            jumpToDetails(id) {
                this.$router.push({name: 'hotelDetail', params: {hotelId: id}});
            },
            onSelect(value) {
                console.log('onSelect', value);
            },

            handleSearch(value) {
                console.log(value);
                this.searchToUpdateHotelList(value);
                // this.dataSource = value ? this.searchResult(value) : [];
            },
            showDrawer() {
                this.visible = true;
            },
            onClose() {
                this.visible = false;
            },
            // highRateFirst(){
            //     this.hotelList.sort(sortRate)
            //     function sortRate(first,second){
            //         return first.rate - second.rate
            //     }
            // }
            highRateFirst() {
                this.hotelList.sort(function (a, b) {
                    return b.rate - a.rate;
                })
            },
            lowRateFirst(){
                this.hotelList.sort(function (a, b) {
                    return a.rate - b.rate;
                })
            },
            highFirst(){
                this.getHotelCold();
            },
            lowFirst(){
                this.getHotelHot();
            }
        },
    };
</script>

<style lang="less" scoped>
    .filterButton {
        margin-left: 85%;
        text-align: right;
    }
    .highAndLow {
        margin-top: 50px;
        text-align: center;
    }

    .hotelList {
        text-align: center;
        padding: 50px 0;


        .global-search-wrapper {
            display: inline;
            padding-right: 50px;
            width: 300px;
        }

        .global-search {
            width: 100%;
        }

        .global-search.ant-select-auto-complete .ant-select-selection--single {
            margin-right: -46px;
        }

        .global-search.ant-select-auto-complete .ant-input-affix-wrapper .ant-input:not(:last-child) {
            padding-right: 62px;
        }

        .global-search.ant-select-auto-complete .ant-input-affix-wrapper .ant-input-suffix button {
            border-top-left-radius: 0;
            border-bottom-left-radius: 0;
        }

        .global-search-item {
            display: flex;
        }

        .global-search-item-desc {
            flex: auto;
            text-overflow: ellipsis;
            overflow: hidden;
        }

        .global-search-item-count {
            flex: none;
        }

        .emptyBox {
            height: 0;
            margin: 10px 10px
        }

        .card-wrapper {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            flex-grow: 3;
            min-height: 800px
        }

        .card-wrapper .card-item {
            margin: 30px;
            position: relative;
            height: 188px;
        }
    }
</style>
