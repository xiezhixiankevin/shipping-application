<template>
    <div class="page-background">
        <top-bar :userType="'User'"></top-bar>
        <div class="container">
            <div class="info-block">
                <div class="header clearfix">
                    <p>货物信息</p>
                </div>
                <form class="form">
                    <label class="form-item">
                        货物名称
                        <input type="text" class="input" v-model="cargoName">
                    </label>
                    <label class="form-item">
                        货物描述
                        <input type="text" class="input" v-model="cargoInfo">
                    </label>
                    <label class="form-item">
                        货物重量
                        <input type="text" class="input" v-model="cargoWeight">
                    </label>
                    <label class="form-item">
                        承运商(请选择)
                        <select class="input" v-model="selectedCarrier">
                            <option v-for="carrier in carriers" :key="carrier.id" :value="carrier.id">
                                {{ carrier.name }}
                            </option>
                        </select>
                    </label>

                    <label class="form-item">
                        接受签收时间段
                        <input type="date" class="input" v-model="latestDeliveryDate">
                        <span class="line">-</span>
                        <input type="time" class="input" v-model="latestDeliveryTime">
                    </label>
                    <div class="form-item urgency-refrigerated">
                        <label class="form-subitem">
                            加急级别
                            <select class="input" v-model="urgencyLevel">
                                <option value=1>低</option>
                                <option value=2>中</option>
                                <option value=3>高</option>
                            </select>
                        </label>
                        <label class="form-subitem">
                            是否冷藏
                            <input type="checkbox" class="checkbox" v-model="isRefrigerated">
                        </label>
                    </div>

                </form>
            </div>
            <div class="info-block">
                <div class="header clearfix">
                    <p>寄件人信息</p>
                </div>
                <form class="form">
                    <label class="form-item">
                        寄件人姓名
                        <input type="text" class="input" v-model="senderName">
                    </label>
                    <label class="form-item">
                        寄件人地区(请选择)
                        <select class="input" v-model="selectedRegionSend">
                            <option v-for="region in regions" :key="region.id" :value="region.city">
                                {{ region.city }}
                            </option>
                        </select>
                    </label>
                    <label class="form-item">
                        寄件人地址
                        <input type="text" class="input" v-model="addressSender">
                    </label>

                    <label class="form-item">
                        寄件人手机号
                        <input type="text" class="input" v-model="senderPhoneNumber">
                    </label>
                </form>
            </div>
            <div class="info-block">
                <div class="header clearfix">
                    <p>收件人信息</p>
                </div>
                <form class="form">
                    <label class="form-item">
                        收件人姓名
                        <input type="text" class="input" v-model="receiverName">
                    </label>
                    <label class="form-item">
                        收件人地区(请选择)
                        <select class="input" v-model="selectedRegionReceive">
                            <option v-for="region in regions" :key="region.id" :value="region.city">
                                {{ region.city }}
                            </option>
                        </select>
                    </label>
                    <label class="form-item">
                        收件人地址
                        <input type="text" class="input" v-model="addressReceiver">
                    </label>
                    <label class="form-item">
                        收件人手机号
                        <input type="text" class="input" v-model="receiverPhoneNumber">
                    </label>

                </form>
            </div>
            <p v-if="distance">距离：{{ distance }}公里</p>
            <div class="form-item">
                <button class="button" @click="calculateDistance">距离验证</button>
                <button class="button" @click="createOrder">创建运单</button>
            </div>

        </div>
        <br>
        <br>
        <br>
    </div>
</template>
  
<script>
import Cookies from "js-cookie";
import axios from 'axios';
import TopBar from '../TopBar.vue'

export default {
    components: {
    TopBar
  },
    data() {
        return {
            username: "HCB",
            selectedCarrier: null,
            selectedRegionSend: null,
            selectedRegionReceive: null,
            carriers: [],
            token: "",
            urgencyLevel: 1,
            isRefrigerated: false,
            regions: [],
            addressSender: "",
            addressReceiver: "",
            distance: 0,

            cargoName: "",
            cargoWeight: null,
            cargoInfo: "",
            senderName: "",
            receiverName: "",
            senderPhoneNumber: "",
            receiverPhoneNumber: "",

            latestDeliveryDate: "",
            latestDeliveryTime: "",
        };
    },
    created() {
        this.token = Cookies.get("token");
        console.log(this.token); // 打印出token
        axios
            .get('/carrier/get-all-carrier-name')
            .then((response) => {
                if (response.data.success && response.data.code === 20000) {
                    this.carriers = response.data.data.carrierName;
                } else {
                    console.error('获取承运商数据失败:', response.data.message);
                }
            })
            .catch((error) => {
                console.error('获取承运商数据失败:', error);
            });
        axios
            .get('/city/All')
            .then((response) => {
                if (response.data.success && response.data.code === 20000) {
                    this.regions = response.data.data.city;
                    console.log(response.data.data);
                } else {
                    console.error('获取城市数据失败:', response.data.message);
                }
            })
            .catch((error) => {
                console.error('获取城市数据失败:', error);
            });
    },
    methods: {
        async getCoordinates(address) {
            return new Promise((resolve, reject) => {
                this.$jsonp(`https://api.map.baidu.com/geocoding/v3/?address=${address}&output=json&ak=3uypRMoEUQhvyFQhtC6BvDf3v9glF9w9&callback=showLocation`, {
                    callbackName: 'showLocation'
                })
                    .then(response => {
                        if (response.status === 0 && response.result.confidence >= 60) {
                            resolve(response.result.location);
                        } else {
                            reject(new Error("地址解析失败"));
                            alert("无法找您输入的地址，请尝试更准确地描述。")
                        }
                    })
                    .catch(error => {
                        reject(error);
                    });
            });
        },

        async calculateDistance() {
            const senderCoordinates = await this.getCoordinates(this.selectedRegionSend + this.addressSender);
            const receiverCoordinates = await this.getCoordinates(this.selectedRegionReceive + this.addressReceiver);

            if (senderCoordinates && receiverCoordinates) {
                const distance = this.calculateDistanceBetweenCoordinates(senderCoordinates, receiverCoordinates);
                this.distance = distance.toFixed(2);
            } else {
                this.distance = 0;
                alert("无法计算距离，请检查地址是否正确。");
            }
        },
        calculateDistanceBetweenCoordinates(coord1, coord2) {
            const toRadians = (degrees) => degrees * (Math.PI / 180);
            const R = 6371; // 地球半径，单位：千米
            const dLat = toRadians(coord2.lat - coord1.lat);
            const dLon = toRadians(coord2.lng - coord1.lng);
            const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(toRadians(coord1.lat)) * Math.cos(toRadians(coord2.lat)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return R * c;
        },

        async createOrder() {
            const combinedDateTime = `${this.latestDeliveryDate}T${this.latestDeliveryTime}:00`;
            const latestDeliveryTime = new Date(combinedDateTime);
            const latestDeliveryTimeISOString = latestDeliveryTime.toISOString();
            if (
                !this.selectedCarrier ||
                !this.selectedRegionSend ||
                !this.selectedRegionReceive ||
                !this.addressSender ||
                !this.addressReceiver ||
                !this.cargoName ||
                !this.cargoWeight ||
                !this.cargoInfo ||
                !this.senderPhoneNumber ||
                !this.receiverPhoneNumber ||
                !this.latestDeliveryDate ||
                !this.latestDeliveryTime
            ) {
                alert("请填写所有必需的字段");
                return;
            }

            if (this.distance === 0) {
                alert("请先进行距离验证后提交运单");
                return;
            }

            try {
                const shippingOrder = {
                    providerId: this.selectedCarrier,
                    senderName: this.senderName,
                    senderAddress: this.selectedRegionSend,
                    senderPhoneNumber: this.senderPhoneNumber,
                    receiverName: this.receiverName,
                    receiverAddress: this.selectedRegionReceive,
                    receiverPhoneNumber: this.receiverPhoneNumber,
                    cargoName: this.cargoName,
                    cargoWeight: this.cargoWeight,
                    cargoInfo: this.cargoInfo,
                    estimateDistance: this.distance,
                    urgentLevel: this.urgencyLevel,
                    refrigerated: this.isRefrigerated,
                    latestDeliveryTime: latestDeliveryTimeISOString,
                };

                const response = await axios.post("/order/create", shippingOrder);

                if (response.data.success && response.data.code === 20000) {
                    alert("订单创建成功");
                } else {
                    console.error("创建订单失败:", response.data.message);
                    alert("创建订单失败，请稍后重试，错误原因：", response.data.message);
                }
            } catch (error) {
                console.error("创建订单失败:", error);
                alert("创建订单失败，请稍后重试");
            }
        },
    }


}
</script>

<style scoped>


.page-background {
    padding-top: 60px;
}

.checkbox {
    width: 20px;
    height: 20px;
    cursor: pointer;
}

.container {
    display: flex;
    flex-direction: column;
    /* gap: 0px; */
    align-items: center;
}

.info-block {
    width: 100%;
    max-width: 600px;
    background-color: #fffffe;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 10px;
}

.header {
    font-size: 30px;
    font-weight: bold;
    color: #272343;
    margin-bottom: 20px;
}

.clearfix {
    font-size: 40px;
    color: #2d334a;
    display: block;
    text-align: left;
}

.form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.form-item {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: 5px;
}

.input {
    flex-grow: 1;
    background: transparent;
    border: none;
    border-bottom: 3px solid #272343;
    font-size: 20px;
    outline: none;
}

.input:focus {
    border-color: #ffd803;
    color: #272343;
}

.line {
    display: inline-block;
    margin: 0 10px;
}

.button {
    background-color: #ffd803;
    color: #272343;
    border: none;
    border-radius: 8px;
    padding: 15px 100px;
    cursor: pointer;
    font-size: 20px;
}

.button:hover {
    background-color: #ffca1e;
}

.urgency-refrigerated {
    display: flex;
    justify-content: space-between;
}

.form-subitem {
    display: flex;
    align-items: center;
    gap: 5px;
}
</style>

  