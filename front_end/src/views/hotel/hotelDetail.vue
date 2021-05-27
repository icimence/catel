<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card>
                        <img
                                :src="currentHotelInfo.pic"
                                alt="hotelPic"
                                referrerPolicy="no-referrer"
                                slot="cover"
                                style="width: 160px;height: 160px"
                        />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.phoneNumber">
                            <span class="label">服务热线:</span>
                            <span class="value">{{ currentHotelInfo.phoneNumber }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ currentHotelInfo.rate }}分</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                             <a-rate :value="currentHotelInfo.rate" allowHalf disabled style="font-size: 15px"/>
                        </div>
<!--                        <div class="items" v-if="currentHotelInfo.description">-->
<!--                            <span class="label">酒店简介:</span>-->
<!--                            <span class="value">{{ currentHotelInfo.description }}</span>-->
<!--                        </div>-->
                        <div>
                            <a-button @click="jumpToHome" type="primary"><a-icon type="home" />返回首页</a-button>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane key="1" tab="房间信息">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane key="2" tab="酒店简介">
                        <h2 style="text-align: center">酒店简介</h2>
                        <p style="text-align: center;font-size: 18px;word-wrap: break-word;opacity: 0.5;margin-top: 30px" v-if="this.currentHotelInfo.description===''">目前没有酒店介绍</p>
                        <p style="text-align: center;font-size: 15px;word-wrap: break-word" v-else>{{
                            currentHotelInfo.description}}</p>
                    </a-tab-pane>
                    <a-tab-pane key="3" tab="酒店促销公告">
                        <h2 style="text-align: center">酒店促销公告</h2>
                        <p style="text-align: center;font-size: 18px;word-wrap: break-word;opacity: 0.5;margin-top: 30px" v-if="this.currentHotelInfo.announcement===''">目前没有促销公告</p>
                        <p style="text-align: center;font-size: 18px;word-wrap: break-word" v-else>{{
                        currentHotelInfo.announcement}}</p>
                    </a-tab-pane>
                    <a-tab-pane key="4" tab="用户评论">
                        <a-list
                                class="comment-list"
                                :header="`${commentList.length}条评论`"
                                item-layout="horizontal"
                                :data-source="commentList"
                        >
                            <a-list-item slot="renderItem" slot-scope="item">
                                <a-comment :author="item.username" :avatar="item.avatar" class="pinglun">
                                    <p slot="content" class="pinglunInfo" >
                                        <a-rate :default-value="item.score" disabled class="star"/>
                                        <span class="score">
                                            {{ item.score }}分
                                        </span>
                                        <br>
                                        {{ item.content }}
                                    </p>
                                </a-comment>
                            </a-list-item>
                        </a-list>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex';
    import RoomList from './components/roomList';

    export default {
        name: 'hotelDetail',
        components: {
            RoomList,
        },
        data() {
            return {};
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
                'commentList',
            ]),
        },
        mounted() {
            this.set_currentHotelId(Number(this.$route.params.hotelId))
            this.getHotelById()
            this.getCommentList()
        },
        beforeRouteUpdate(to, from, next) {
            this.set_currentHotelId(Number(to.params.hotelId))
            this.getHotelById()
            this.getCommentList()
            next()
        },
        methods: {
            ...mapMutations([
                'set_currentHotelId',
            ]),
            ...mapActions([
                'getHotelById',
                'getCommentList'
            ]),
            jumpToHome() {
                this.$router.push({name: 'hotelList'})
            },
        },
    };
</script>
<style lang="less" scoped>

    .hotelDetailCard {
        padding: 50px 50px;
    }

    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;

        .info {
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;

            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .label {
                    margin-right: 10px;
                    font-size: 18px;
                }

                .value {
                    margin-right: 15px
                }
            }
        }
    }
    .pinglunInfo {
        font-size: 14px;
        word-wrap: break-word;
    }
    .star {
        transform:scale(0.7);
        margin-left: -16px;
    }
    .pinglun {
        transform:scale(1.2);
        width: 500px;
        margin-left: 50px;
        margin-top: 40px;
    }
    .score {
        font-size: 15px;
        margin-left: -20px;
    }
</style>
