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
    <el-table :data="tableData" border stripe     @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="姓名" >
      </el-table-column>
      <el-table-column prop="description" label="描述" >
      </el-table-column>
      <el-table-column prop="flag" label="唯一标识" >
      </el-table-column>


      <el-table-column >
        <!--            <div class="dis">-->
<!--        删除提示-->
        <template slot-scope="scope">
          <el-button type="success" @click="menuEdit(scope)" >分配菜单 <i class="el-icon-edit"></i></el-button>
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

<!--    分页-->
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

    <!--        添加角色弹窗-->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" v-if="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="角色名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <!--        菜单分配弹窗-->
    <el-dialog title="菜单分配" :visible.sync="menuDialogFormVisible" v-if="menuDialogFormVisible" width="30%">
      <el-tree
          :data="menuData"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="expends"
          :default-checked-keys="checks"
          :props="defaultProps">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Role",
  data(){
    return{
      //页签
      pageNum:1,
      pageSize:5,
      total:0,
      //搜索变量
      selectName:"",
      //添加角色
      dialogFormVisible:false,
      form:{},
      //分配菜单
      menuDialogFormVisible:false,
      menuData: [],
      roleId:0,
      //子菜单名称 和菜单名称
      defaultProps: {
        children: 'childrenMenu',
        label: 'name'
      },
      //勾选参数数组
      expends:[],
      checks:[],
      //批量操作
      multipleSelection: [],
      tableData: [],
    }
  },
  methods:{

    //请求分页
    load(){
      this.request.get("/role/page",{
        params:{
          pageNum :this.pageNum,
          pageSize : this.pageSize,
          rolename :this.selectName,
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
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum;
      this.load()
    },
    // 重置列表
    reset(){
      this.selectName ="";
      this.load()
    },
    // 删除byId
    deleteById(id){
      this.request.delete(`/role/deleteById/`+id).then(res=>{
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
    //批量操作
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    //批量删除
    delBatch(){
      let ids = this.multipleSelection.map(v =>v.id)
      this.request.post("/role/deleteBatch",ids).then(res =>{
        if(res.code === '200'){
          this.$message.success("批量删除成功");
          this.load();
        }else {
          this.$message.error(res.msg)
        }

      })
    },
    //显示添加窗口
    insert(){
      this.form = {};
      this.dialogFormVisible = true
    },
    //发送角色保存请求
    save(){
      this.request.post("/role/save",this.form).then(res =>{
        if(res.code ==='200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error(res.msg)
        }
      })

    },
    //修改角色弹框
    edit(scope){
      this.dialogFormVisible = true
      this.form =scope.row
    },


    //分配菜单弹框 请求角色菜单数据
    menuEdit(scope){
      this.menuDialogFormVisible = true
      this.roleId = scope.row.id

      //获取菜单列表
      this.request.get("/menu").then(res => {
        if(res.code === "200"){
          this.menuData = res.data;
          //后台菜单数据映射为 id数组 设置为默认展开
          this.expends = this.menuData.map(v=> v.id)
        }
        else
          this.$message.error("获取菜单列表失败")
      })

      //获取选中用户的菜单
      this.request.get("/role/roleMenu/"+this.roleId).then(res =>{
        if(res.code ==='200'){
          this.checks = res.data
        }else {
          this.$message.error("查询用户菜单失败")
        }
      });




    },
    //分配菜单保存请求
    saveRoleMenu(){
      // console.log(this.$refs.tree.getCheckedKeys());

      this.request.post("/role/roleMenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then(res =>{
          if(res.code ==='200'){
            this.$message.success("分配成功")
            this.menuDialogFormVisible = false
          }else {
            this.$message.error("分配失败")
          }
      });
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