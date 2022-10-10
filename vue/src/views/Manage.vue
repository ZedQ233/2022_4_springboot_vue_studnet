<template>
<div>
  <el-container>
    <el-aside :width="sideWidth +'px'" style="background-color: rgb(238, 241, 246)" >
      <Aside/>
    </el-aside>

    <el-container>
      <!--      头部-->
      <el-header style=" border-bottom: 1px solid antiquewhite">

        <Header/>
      </el-header>

      <!--      主要内容-->
      <el-main>
        <router-view @refreshUser="getUser" />
      </el-main>

    </el-container>

  </el-container>
</div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
export default {
  name: 'HomeView',
  data() {
    return {
      // 菜单默认宽度
      sideWidth:200,
      //用户名
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{},

    }
  },
  mounted() {
    this.$bus.$on('getSideWidth',data =>{
      this.sideWidth = data;
    })

  },


  methods:{
    getUser(){
      this.request.post("/user/username/"+this.user.username).then(res => {
        if (res.code === '200') {

          let user = res.data
          console.log(user)
          user.token =JSON.parse(localStorage.getItem("user")).token
          this.user.token = user.token
          this.user.avatarUrl = user.avatarUrl
          this.user.password = user.password
          this.user.username = user.username
          this.user.nickname = user.nickname
          localStorage.setItem("user",JSON.stringify(this.user))

          //做bus 传参到header
          this.$bus.$emit ('getUser',this.user)


        }
      })
    },

  },
  components: {
    Header,
    Aside

  }
}
</script>
