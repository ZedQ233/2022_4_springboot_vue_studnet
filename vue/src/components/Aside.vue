<template>
  <!--      导航栏-->
  <el-menu :default-openeds="opens" style="min-height: 100vh"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"

           router
           >
<!--           @select="handleSelect"-->


    <!--        菜单背景色-->
    <!--        background-color="rgb(48,65,86)"-->
    <!--        菜单文字背景色-->
    <!--        text-color="#fff"-->
    <!--        菜单选中颜色-->
    <!--        active-text-color="#ffd04b"-->

    <!--        首页图标-->
    <div style="height: 59px;  line-height: 59px;text-align: center">
      <img src="../assets/logo.png" style="width: 30px; height: 25px;position: relative;top: 5px">
      <b style="color: white" v-show="LogoText">后台管理系统</b>
    </div>


    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="chil in item.childrenMenu" :key="chil.id">
            <el-menu-item :index="chil.path">
              <i :class="chil.icon"></i>
              <span slot="title">{{ chil.name }}</span>
            </el-menu-item>
          </div >


        </el-submenu>
      </div>
    </div>

    <!--        菜单-->

<!--    <el-menu-item index="/">-->
<!--      <i class="el-icon-house"></i>-->
<!--      <span slot="title">主页</span>-->
<!--    </el-menu-item>-->

<!--    <el-submenu index="2">-->
<!--      <template slot="title"><i class="el-icon-menu"></i>-->
<!--        <span slot="title">系统管理</span>-->
<!--      </template>-->
<!--      <el-menu-item-group>-->

<!--        <el-menu-item index="/user">-->
<!--          <i class="el-icon-user"></i>-->
<!--          <span slot="title">用户管理</span>-->
<!--        </el-menu-item>-->

<!--        <el-menu-item index="/role">-->
<!--          <i class="el-icon-user"></i>-->
<!--          <span slot="title">角色管理</span>-->
<!--        </el-menu-item>-->

<!--        <el-menu-item index="/menu">-->
<!--          <i class="el-icon-menu"></i>-->
<!--          <span slot="title">菜单管理</span>-->
<!--        </el-menu-item>-->

<!--        <el-menu-item index="/file">-->
<!--          <i class="el-icon-document"></i>-->
<!--          <span slot="title">文件管理</span>-->
<!--        </el-menu-item>-->

<!--      </el-menu-item-group>-->
<!--    </el-submenu>-->

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      isCollapse:false,
      LogoText : true,
      menus: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).menus:{},
      opens: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).menus.map(v=>v.id+''):{}
    }
  },
  mounted() {
    this.$bus.$on('getIsCollapse',data =>{
      this.isCollapse = data
      console.log(1)
      console.log(data)
    })
    this.$bus.$on('getLogoText',data =>{
      this.LogoText = data;
      console.log(2)
      console.log(data)
    })
    console.log(this.$route.name)
  },
  methods:{

    //导航
    // handleSelect(index){
    //   // console.log(this.$route)
    //   // console.log(index)
    //   // console.log(this.$route.fullPath)
    //   // console.log(this.$router.options.routes)
    //   // const x = this.$router.options.routes
    //   // x.filter()
    //   // console.log(this.$route)
    //   // this.$route.fullPath.split('/')
    // }
  }

}
</script>

<style scoped>

</style>