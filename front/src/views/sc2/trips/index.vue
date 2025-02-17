<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="行程名称" prop="tripName">
        <el-input
          v-model="queryParams.tripName"
          placeholder="请输入行程名称"
          clearable
          @keyup.enter="handleQuery"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="行程起始日" prop="tripStartDate">
        <el-date-picker clearable
          v-model="queryParams.tripStartDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择行程起始日"
          style="width: 200px">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="行程终止日" prop="tripEndDate">
        <el-date-picker clearable
          v-model="queryParams.tripEndDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择行程终止日"
          style="width: 200px">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="行程起始地" prop="tripDepaturePlace">
        <el-input
          v-model="queryParams.tripDepaturePlace"
          placeholder="请输入行程起始地"
          clearable
          @keyup.enter="handleQuery"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="行程目的地" prop="tripDestination">
        <el-input
          v-model="queryParams.tripDestination"
          placeholder="请输入行程目的地"
          clearable
          @keyup.enter="handleQuery"
          style="width: 200px"
        />
      </el-form-item>
      <br>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['sc2:trips:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['sc2:trips:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['sc2:trips:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['sc2:trips:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tripsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="行程主键" align="center" prop="tripId" />-->
      <el-table-column label="行程名称" align="center" prop="tripName" />
      <el-table-column label="行程起始日" align="center" prop="tripStartDate" width="150">
        <template #default="scope">
          <span>{{ parseTime(scope.row.tripStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="行程终止日" align="center" prop="tripEndDate" width="150">
        <template #default="scope">
          <span>{{ parseTime(scope.row.tripEndDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="行程起始地" align="center" prop="tripDepaturePlace" />
      <el-table-column label="行程目的地" align="center" prop="tripDestination" />
      <el-table-column label="行程预算" align="center" prop="tripBudget" />
      <el-table-column label="行程预算币种" align="center" prop="tripBudgetCurrency">
        <template #default="scope">
          <dict-tag :options="sc2_trip_budget_currency" :value="scope.row.tripBudgetCurrency"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="备注" align="center" prop="remark" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['sc2:trips:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['sc2:trips:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改行程对话框 -->
    <el-dialog :title="title" v-model="open" width="350px" append-to-body>
      <el-form ref="tripsRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="行程名称" prop="tripName" >
          <el-input v-model="form.tripName" placeholder="请输入行程名称" :disabled="isDisabled"/>
        </el-form-item>
        <el-form-item label="行程起始日" prop="tripStartDate">
          <el-date-picker clearable
            v-model="form.tripStartDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择行程起始日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="行程终止日" prop="tripEndDate">
          <el-date-picker clearable
            v-model="form.tripEndDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择行程终止日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="行程起始地" prop="tripDepaturePlace">
          <el-input v-model="form.tripDepaturePlace" placeholder="请输入行程起始地" />
        </el-form-item>
        <el-form-item label="行程目的地" prop="tripDestination">
          <el-input v-model="form.tripDestination" placeholder="请输入行程目的地" />
        </el-form-item>
        <el-form-item label="行程预算" prop="tripBudget">
          <el-input v-model="form.tripBudget" placeholder="请输入行程预算" />
        </el-form-item>
        <el-form-item label="行程预算币种" prop="tripBudgetCurrency">
          <el-select v-model="form.tripBudgetCurrency" placeholder="请选择行程预算币种">
            <el-option
              v-for="dict in sc2_trip_budget_currency"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Trips">
import { listTrips, getTrips, delTrips, addTrips, updateTrips } from "@/api/sc2/trips";

const { proxy } = getCurrentInstance();
const { sc2_trip_budget_currency } = proxy.useDict('sc2_trip_budget_currency');

const tripsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const idsName = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const isDisabled = ref();

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    tripName: null,
    tripStartDate: null,
    tripEndDate: null,
    tripDepaturePlace: null,
    tripDestination: null,
  },
  rules: {
    tripName: [
      { required: true, message: "行程名称不能为空", trigger: "blur" }
    ],
    tripEndDate: [
      {
        validator: (rule, value, callback) => {
          const startDate = form.value.tripStartDate;
          const endDate = value;
          // 校验起始日期和终止日期要么都为空，要么都不为空
          if (startDate && !endDate) {
            callback(new Error("请选择行程的终止日期！"));
          } else if (!startDate && endDate) {
            callback(new Error("请选择行程的起始日期！"));
          } else if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
            callback(new Error("行程的终止日期不得早于起始日期，请重新选择！"));
          } else {
            callback(); // 校验通过
          }
        },
        trigger: "blur"
      }
    ],
    tripBudget: [
      {
        required: false,
        message: "预算不能为空",
        trigger: "blur"
      },
      {
        validator: (rule, value, callback) => {
          if (value !== undefined && value !== null && value !== "") { // 仅在用户填写预算时调起校验
            const budget = parseInt(value, 10);
            if (isNaN(budget) || budget < 0) {
              callback(new Error("预算必须为非负整数，请重新输入！"));
            } else {
              callback();
            }
          } else {
            callback(); // 若预算为空，跳过校验
          }
        },
        trigger: "blur" // 在失去焦点时校验
      }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询行程列表 */
function getList() {
  loading.value = true;
  listTrips(queryParams.value).then(response => {
    tripsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    tripId: null,
    tripName: null,
    tripStartDate: null,
    tripEndDate: null,
    tripDepaturePlace: null,
    tripDestination: null,
    tripBudget: null,
    tripBudgetCurrency: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("tripsRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.tripId);
  idsName.value = selection.map(item => item.tripName);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加行程";
  isDisabled.value = false; // 添加状态下可编辑行程名称
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _tripId = row.tripId || ids.value
  getTrips(_tripId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改行程";
  });
  isDisabled.value = true; // 修改状态下不可编辑行程名称
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["tripsRef"].validate(valid => {
    if (valid) {
      if (form.value.tripId != null) {
        updateTrips(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTrips(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _tripIds = row.tripId || ids.value;
  const _tripName = row.tripName || idsName.value;
  // proxy.$modal.confirm('是否确认删除行程编号为"' + _tripIds + '"的数据项？').then(function() {
  proxy.$modal.confirm('是否确认删除行程名称为"' + _tripName + '"的数据项？').then(function() {
    return delTrips(_tripIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('sc2/trips/export', {
    ...queryParams.value
  }, `trips_${new Date().getTime()}.xlsx`)
}

getList();
</script>
