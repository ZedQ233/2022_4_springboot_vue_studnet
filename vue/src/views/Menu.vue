<template>
  <div>



    <div style="margin: 10px 0">
      <el-input  style="width: 200px" placeholder="请输入名称"  v-model="selectName"  suffix-icon="el-icon-search" class="mr-5"></el-input>
      <el-button class="mr-5" type="primary" @click="load">搜索</el-button>
      <el-button class="mr-5" type="waring" @click="reset">重置</el-button>


    </div>

<!--    用户操作栏-->
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

    </div>


<!--    表单数据-->
    <el-table :data="tableData" border stripe
              row-key="id"
              default-expand-all
              :tree-props="{children: 'childrenMenu', hasChildren: 'hasChildren'}"
              @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column prop="id" label="ID" width="120">
      </el-table-column>
      <el-table-column prop="name" label="名称" width="120">
      </el-table-column>
      <el-table-column prop="path" label="路径" width="120">
      </el-table-column>
      <el-table-column label="图标"  class-name="fontSize18" align="center" lable-class-name="fontSize12">
        <template slot-scope="scope">
          <i :class="scope.row.icon"/>
        </template>

      </el-table-column>
      <el-table-column prop="description" label="描述" width="120">
      </el-table-column>
      <el-table-column prop="pagePath" label="页面路径" width="120">
      </el-table-column>


      <el-table-column >
        <!--            <div class="dis">-->
<!--        删除提示-->
        <template slot-scope="scope">
          <el-button type="success" @click="insert(scope.row.id)" >新增子菜单 <i class="el-icon-edit"></i></el-button>
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



    <!--        添加菜单弹窗-->
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" v-if="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="菜单名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" >
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in option" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value"/> {{item.name}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
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
  name: "Menu",
  data(){
    return{
      //页签
      pageNum:1,
      pageSize:5,
      total:0,
      //搜索变量
      selectName:"",
      //添加菜单
      dialogFormVisible:false,
      form:{},
      //批量操作
      multipleSelection: [],
      tableData: [],
      //菜单弹窗
      option: [],
    }
  },
  methods:{
    load(){
      this.request.get("/menu/",{
        params:{
          name:""
        }
      }).then(res =>{
        if(res.code === '200'){
          console.log(res)
          this.tableData = res.data
        }else {
          this.$message.error(res.msg)
        }
      })
    },

    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load()
    },

    //删除byId
    deleteById(id){
      this.request.delete(`/menu/deleteById/`+id).then(res=>{
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
    //重置
    reset(){
      this.selectName ="";
      this.load()
    },

    // 添加菜单
    insert(pid){
      this.form = {};
      this.dialogFormVisible = true
      if(pid.isNumber){
        this.form.pid = pid
      }

      //请求icon数据
      this.request.get("/menu/icons").then(res =>{
        if(res.code === '200'){
          this.option = res.data
        }else {
          this.$message.error(res.msg)
        }
      })

    },

    //添加菜单提交请求
    save(){
      this.request.post("/menu/save",this.form).then(res =>{
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

      //请求icon数据
      this.request.get("/menu/icons").then(res =>{
        if(res.code === '200'){
          this.option = res.data
        }else {
          this.$message.error(res.msg)
        }
      })


    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v =>v.id)
      this.request.post("/menu/deleteBatch",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功");
          this.load();
        }else {
          this.$message.error(res.msg)
        }

      })
    },
    addChilMenu(id){

    }

  },
  created() {
    //请求分页查询数据
    this.load();
  },
}
</script>

<style>
.fontSize18{
  font-size: 18px;
}
.fontSize12{
  font-size: 12px;
}
</style>