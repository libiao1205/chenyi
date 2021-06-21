<template>
  <a-modal
    title="新增数据集"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :maskClosable="false"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">
            <a-form-item
              label="名称"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-input placeholder="请输入名称" v-decorator="['name', {rules: [{required: true, message: '请输入名称！'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              label="唯一编码"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              has-feedback
            >
              <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{required: true, message: '请输入编码！'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :md="12" :sm="24">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="排序"
            >
              <a-input-number placeholder="请输入排序" style="width: 100%" v-decorator="['sort', { initialValue: 100 }]" :min="1" :max="1000" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <div style="display: flex; justify-content: flex-start;">
              <span style="color: rgba(0, 0, 0, 0.85);margin-left: 2px">选择元数据表：</span>
              <div style="display: flex;justify-content: center">
                <Transfer
                  :titles="['未选', '已选']"
                  :dataSource="mockData"
                  :targetKeys="targetKeys"
                  @change="onChange"
                  :render="render"
                  :pagination="true"
                  :showSearch="true"
                />
              </div>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
  import { dataCollectAdd } from '@/api/modular/main/dataCollect'
  import { metadataTableList } from '@/api/modular/main/metadataTable'
  import { mapState, mapGetters } from 'vuex'
  import { Transfer } from 'ant-design-vue/es'
  export default {
    components: {
      Transfer
    },
    computed: {
      ...mapState({
        dataSourceType: state => state.constant.dataSourceType,
        statusList: state => state.constant.statusList
      }),
      ...mapGetters({
        getDataSource: 'constant/getDataSource'
      })
    },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        addChildren: false,
        dataSourceColumns: [],
        mockData: [],
        targetKeys: [],
        onChange: (newTargetKeys, direction, moveKeys) => {
          this.targetKeys = newTargetKeys
        },
        render: (item) => {
          return item.title
        }
      }
    },
    methods: {
      // 初始化方法
      add (record) {
        if (record !== undefined) {
          this.addChildren = true
          setTimeout(() => {
            this.form.setFieldsValue({ parent: record.name })
          }, 100)
        } else {
          this.addChildren = false
        }
        this.visible = true
        this.init()
      },
      init() {
        metadataTableList().then((res) => {
          this.mockData = []
          res.data.forEach(row => {
            const data = {
              key: row.code,
              title: row.name
            }
            this.mockData.push(data)
          })
        })
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            values.tableCodes = this.targetKeys
            dataCollectAdd(values).then((res) => {
              this.confirmLoading = false
              if (res.success) {
                this.$message.success('新增成功')
                this.handleCancel()
                this.$emit('ok', values)
              } else {
                this.$message.error('新增失败：' + res.message)
              }
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      changeDataSourceType(code) {
        this.dataSourceColumns = this.getDataSource(code)
      }
    }
  }
</script>
<style  lang="less">
  .ant-transfer-list{
    width: 328px;
    height: 450px;
  }
</style>
