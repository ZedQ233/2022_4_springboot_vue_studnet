<template>
  <div>



    <div style="margin: 10px 0">
      <el-input  style="width: 200px" placeholder="请输入名称"  v-model="selectName"  suffix-icon="el-icon-search" class="mr-5"></el-input>
      <el-input  style="width: 200px" placeholder="请输入邮箱" v-model="selectEmail" suffix-icon="el-icon-message" class="mr-5"></el-input>
      <el-input  style="width: 200px" placeholder="请输入地址" v-model="selectAddress" suffix-icon="el-icon-position" class="mr-5"></el-input>
      <el-button class="mr-5" type="primary" @click="load">搜索</el-button>
      <el-button class="mr-5" type="waring" @click="reset">重置</el-button>


    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="insert">新增 <i class="el-icon-circle-plus-outline"></i></el-button>

      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" class="mr-5">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="importUser" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exportUser" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe     @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="username" label="姓名" width="120">
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" width="140">
      </el-table-column>
      <el-table-column prop="address" label="地址">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="140">
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="140">
      </el-table-column>
      <el-table-column >
        <!--            <div class="dis">-->
        <template slot-scope="scope">
          <el-button type="success" @click="edit(scope)" >编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="deleteById(scope.row.id)"
          >
            <el-button type="danger"  slot="reference"  >删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
        <!--            </div>-->

      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!--        添加用户弹窗-->
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" v-if="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      //页签
      pageNum:1,
      pageSize:5,
      total:0,
      //搜索变量
      selectName:"",
      selectEmail:"",
      selectAddress:"",
      //添加用户
      dialogFormVisible:false,
      form:{},
      //批量操作
      multipleSelection: [],
      tableData: [],
    }
  },
  methods:{
    load(){
      this.request.get("/user/page",{
        params:{
          pageNum :this.pageNum,
          pageSize : this.pageSize,
          username:this.selectName,
          email:this.selectEmail,
          address:this.selectAddress
        }
      }).then(res =>{
        if(res.code === '200'){
          console.log(res)
          this.tableData = res.data.records
          this.total = res.data.total
        }else {
          this.$message.error(res.msg)
        }


      })
      // fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.selectName+"&email="+this.selectEmail+"&address="+this.selectAddress).then(res=>res.json()).then(res =>{
      //   this.tableData = res.records;
      //   this.total = res.total;
      // })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load()
    },
    deleteById(id){
      this.request.delete(`/user/deleteById/`+id).then(res=>{
        if(res.code ==='200'){
          this.$message.success("删除成功")
          this.load();
        }else {
          this.$message.error(res.msg)
        }

      }).catch(e =>{
        this.$message.error(e)
      })
    },
    reset(){
      this.selectName ="";
      this.selectEmail="";
      this.selectAddress="";
      this.load()
    },
    insert(){
      this.form = {};
      this.dialogFormVisible = true
    },
    save(){
      this.request.post("/user/save",this.form).then(res =>{
        if(res.code ==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error(res.msg)
        }
      })

    },
    edit(scope){
      this.dialogFormVisible = true
      console.log(scope)
      this.form =scope.row
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v =>v.id)
      this.request.post("/user/deleteBatch",ids).then(res =>{
        if(res.code ='200'){
          this.$message.success("批量删除成功");
          this.load();
        }else {
          this.$message.error(res.msg)
        }

      })
    },
    exportUser(){
      window.open("http://localhost:9090/user/export")
    },
    importUser(){
      this.$message.success("导入成功")
      this.load()
    }
  },
  created() {
    //请求分页查询数据
    this.load();
  },
}
</script>

<style scoped>

</style>