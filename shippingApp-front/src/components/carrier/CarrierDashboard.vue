<template>
    <div class="carrier-info">
        <top-bar :userType="'Carrier'"></top-bar>
    <div class="col2">
      <div class="row">
        <div class="total waiting">
          <span class="countTotal">{{ totalWaiting }}</span>
          <span class="label">未发货</span>
        </div>
        <div class="column" v-for="(value, key) in waitingInfo" :key="key">
          <span class="count">{{ value }}</span>
          <span class="label">{{ key }}</span>
        </div>
        <br>
        <button class="button" @click="goToDeparting">确认发货</button>
      </div>
      <div class="row">
        <div class="total in-transit">
          <span class="countTotal">{{ totalInTransit }}</span>
          <span class="label">已发货</span>
        </div>
        <div class="column" v-for="(value, key) in inTransitInfo" :key="key">
          <span class="count">{{ value }}</span>
          <span class="label">{{ key }}</span>
        </div>
        <br>
        <button class="button" @click="goToDeparted">确认到达</button>
      </div>
    </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import TopBar from '../TopBar.vue'
  
  export default {
    components: {
      TopBar
    },
    data() {
      return {
        waitingInfo: {},
        inTransitInfo: {},
      };
    },
    computed: {
      totalWaiting() {
        return Object.values(this.waitingInfo).reduce((a, b) => a + b, 0);
      },
      totalInTransit() {
        return Object.values(this.inTransitInfo).reduce((a, b) => a + b, 0);
      },
    },
    async created() {
      try {
        const response = await axios.get('/carrier/get-carrier-info');
        const info = response.data.data.info;
  
        this.waitingInfo = {
          '小货车': info.smallTrunkWaiting,
          '大货车': info.bigTrunkWaiting,
          '飞机': info.aircraftWaiting,
        };
  
        this.inTransitInfo = {
          '小货车': info.smallTrunkInTransit,
          '大货车': info.bigTrunkInTransit,
          '飞机': info.aircraftInTransit,
        };
      } catch (error) {
        console.error('Error fetching carrier info:', error);
      }
    },
    methods: {
        goToDeparting() {
            this.$router.push({ path: "/carrier/departingtrunks" });
        },
        goToDeparted() {
            this.$router.push({ path: "/carrier/departedtrunks" });
        },
    }
  };
  </script>
  
  <style scoped>
.carrier-info {
  display: flex;
  flex-direction: column;
  font-family: sans-serif;
  height: 100vh;
  justify-content: center;
  align-items: center;
}
.row {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 15px;
  justify-content: center;
  margin: 100px;
}
.col2 {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.total {
  font-size: 80px;
  color: #272343;
  margin-bottom: 15px;
}
.column {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 20px;
  width: 70px;
}
.count {
  font-size: 35px;
  text-align: center;
}
.label {
  font-size: 15px;
  color: #808080;
  text-align: center;
}
.waiting .label {
  margin-top: 5px;
}
.in-transit .label {
  margin-top: 5px;
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

