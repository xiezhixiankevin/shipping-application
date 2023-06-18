<template>
    <div class="user-dashboard">
        <top-bar :userType="'User'"></top-bar>
        <div class="orders-container">
            <div class="buttons-container">
                <button class="button" @click="goToCreate">新增运单</button>
                <div class="spacer"></div>
                <button class="button" @click="goToQuery">查询运单</button>
            </div>
            <div class="orders-columns-container">
                <div class="orders-column in-transit">
                    <h2>运送中</h2>
                    <info-block v-for="order in inTransitOrders" :key="order.id" :orderData="order"></info-block>
                </div>
                <div class="orders-column completed">
                    <h2>已完成</h2>
                    <info-block v-for="order in completedOrders" :key="order.id" :orderData="order"></info-block>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import InfoBlock from './InfoBlock.vue';
import TopBar from '../TopBar.vue'

export default {
    name: 'UserDashboard',
    components: {
        InfoBlock,
        TopBar
    },
    data() {
        return {
            inTransitOrders: [],
            completedOrders: [],
        };
    },
    created() {
        console.log("yest")
        this.getOrders(false);
        this.getOrders(true);
    },
    methods: {
        async getOrders(ifCompleted) {
            try {
                const response = await axios.get(`/order/get-consumer-orders?ifCompleted=${ifCompleted}`);
                if (response.data.success) {
                    if (ifCompleted) {
                        this.completedOrders = response.data.data.order_list;
                    } else {
                        this.inTransitOrders = response.data.data.order_list;
                    }
                }
            } catch (error) {
                console.error(error);
            }
        },
        goToCreate() {
            this.$router.push({ path: "/order/createorder" });
        },
        goToQuery() {
            this.$router.push({ path: "/order/queryorder" });
        },

    },
};
</script>
  
<style scoped>
.buttons-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
}

.spacer {
    width: 100px;
}

.user-dashboard {
    display: flex;
    justify-content: center;
}

.orders-columns-container {
    display: flex;
    justify-content: space-between;
}

.orders-container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 30px;

}

.orders-column {
    width: 45%;
}

.orders-column h2 {
    font-size: 24px;
    margin-bottom: 10px;
}

.button {
    background-color: #ffd803;
    color: #272343;
    border: none;
    border-radius: 8px;
    padding: 15px 50px;
    cursor: pointer;
    font-size: 20px;
}

.button:hover {
    background-color: #ffca1e;
}
</style>
  