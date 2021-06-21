<template>
  <a-modal
    title="字段约束"
    :width="600"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="formLoading">
      <a-form :form="form">
        <a-form-item
          label="约束条件"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-select style="width: 100%" placeholder="请选择约束条件" v-decorator="['constraintId', {rules: [{ required: true, message: '请选择约束条件！' }]}]" >
            <a-select-option v-for="(item,index) in constraintList" :key="index" :value="item.id" @click="getDetailConstraint(item)">{{ item.value }}</a-select-option>
          </a-select>

        </a-form-item>
        <a-form-item
          label=""
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a style="margin-left:24px ; " @click="showDetails = !showDetails">
            <span v-if="showDetails">隐藏详细约束</span>
            <span v-if="!showDetails">显示详细约束</span>
          </a>

        </a-form-item>

        <a-form-item
          label="约束列表"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          v-if="showDetails && metadataConstraintData.length > 0"
        >

          <a-tree
            v-model="checkedKeys"
            multiple
            checkable
            checkStrictly
            :auto-expand-parent="autoExpandParent"
            :expanded-keys="expandedKeys"
            :tree-data="metadataConstraintData"
            :replaceFields="replaceFields"
            @expand="onExpand"
            @select="onSelect"
            @check="treeCheck"
          />
        </a-form-item>

      </a-form>

    </a-spin>
  </a-modal>
</template>

<script>
import { getSysDictDataTreeByCode, getSysDictDataByParentIdIsNull } from '@/api/modular/system/dictDataManage'
import { metadataColumnFieldConstraint, metadataColumnConstraintList } from '@/api/modular/main/metadataColumn'
import { mapState } from 'vuex'

  export default {
    computed: {
      ...mapState({
        metadataFieldConstraint: state => state.constant.metadataFieldConstraint
      })
    },
    data () {
      return {
        labelCol: {
          style: { 'padding-right': '20px' },
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        metadataConstraintData: [],
        expandedKeys: [],
        checkedKeys: [],
        visible: false,
        confirmLoading: false,
        formLoading: true,
        autoExpandParent: true,
        subValues: [],
        replaceFields: {
          key: 'id'
        },
        form: this.$form.createForm(this),
        constraintList: [],
        code: '',
        showDetails: false
      }
    },

    methods: {
      // 初始化方法
      metadataConstraint (record) {
        this.formLoading = true
        this.code = record.code
        this.visible = true
        this.getConstraint()
        this.expandedConstraintKeys()
        this.showDetails = false
        this.metadataConstraintData = []
      },
      // 获取元数据字段约束
      getConstraint() {
        getSysDictDataByParentIdIsNull({ typeCode: this.metadataFieldConstraint }).then((res) => {
          if (res.success) {
            this.constraintList = res.data
            this.formLoading = false
          }
        }).finally((res) => {
          this.formLoading = false
        })
      },
      getDetailConstraint(item) {
        this.checkedKeys = []
        getSysDictDataTreeByCode({ id: item.id }).then((res) => {
          if (res.success) {
            this.metadataConstraintData = res.data.result
            this.metadataConstraintData.forEach(item => {
              // 默认展开目录级
              this.expandedKeys.push(item.id)
            })
            // 默认选择所有约束
            this.checkedKeys = res.data.idList
            // 查看有没有被当前元数据子段code选择的约束
            metadataColumnConstraintList({ metadataColumnCode: this.code }).then((res) => {
              if (res.data !== null && res.data.length > 0) {
                // 如果有被选择的约束，判断这些约束是否是当前选择的约束条件类型
                const arr = res.data.filter(item => {
                  return this.form.getFieldsValue().constraintId === item.rootDictDataId
                })
                if (arr.length > 0) {
                  this.checkedKeys = []
                  arr.forEach(item => {
                    this.checkedKeys.push(item.dictDataId)
                  })
                }
              }
            })
          }
        }).finally((res) => {
          this.formLoading = false
        })
      },

      /**
       * 此字段已有的约束
       */
      expandedConstraintKeys () {
        this.form.setFieldsValue({
          constraintId: ''
        })
        metadataColumnConstraintList({ metadataColumnCode: this.code }).then((res) => {
          if (res.success) {
            if (res.data != null && res.data.length > 0) {
              this.form.setFieldsValue({
                constraintId: res.data[0].rootDictDataId
              })
              this.getDetailConstraint({ id: res.data[0].rootDictDataId })
              this.checkedKeys = []
              res.data.forEach(item => {
                this.checkedKeys.push(item.dictDataId)
              })
            }
          }
          this.formLoading = false
        })
      },

      treeCheck (checkKeys) {
      },
      onExpand (expandedKeys) {
        this.expandedKeys = expandedKeys
        this.autoExpandParent = false
      },

      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            const metadataConstraints = []
            if (this.checkedKeys.checked === undefined) {
              this.checkedKeys.forEach(item => {
                metadataConstraints.push({ metadataColumnCode: this.code, dictDataId: item, rootDictDataId: values.constraintId })
              })
            } else {
              this.checkedKeys.checked.forEach(item => {
                metadataConstraints.push({ metadataColumnCode: this.code, dictDataId: item, rootDictDataId: values.constraintId })
              })
            }
            metadataColumnFieldConstraint({ metadataConstraints: metadataConstraints }).then((res) => {
              if (res.success) {
                this.$message.success('约束成功')
                this.confirmLoading = false
                this.handleCancel()
              } else {
                this.$message.error('约束失败：' + res.message)
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
        // 清空已选择的
        this.checkedKeys = []
        // 清空已展开的
        this.expandedKeys = []
        this.visible = false
      }
    }
  }
</script>
