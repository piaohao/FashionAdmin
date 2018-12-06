import React, {PureComponent} from 'react';
import {formatMessage, FormattedMessage} from 'umi/locale';
import {Button, Card, Form, Icon, Modal, Input, Dropdown, Menu, message} from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import RenderAuthorized from '@/components/Authorized';
import {getAuthority} from '@/utils/authority';
import StandardTable from "@/components/StandardTable";
import {connect} from 'dva';
import styles from "./User.less";

const Authority = getAuthority();
const Authorized = RenderAuthorized(Authority);
const FormItem = Form.Item;

@Form.create()
class CreateForm extends PureComponent {

  state = {
    formVal: {},
  };

  okHandle = () => {
    const {form, handleSave} = this.props;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      form.resetFields();
      handleSave(fieldsValue);
    });
  };

  render() {
    const {modalVisible, form, action, handleModalVisible, values: formVal} = this.props;
    const title = action === "save" ? "新建用户" : "更新用户";
    return (
      <Modal
        destroyOnClose
        title={title}
        visible={modalVisible}
        onOk={this.okHandle}
        onCancel={() => handleModalVisible()}
      >
        <FormItem>
          {form.getFieldDecorator('id', {initialValue: formVal.id})(<Input type="hidden"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="用户名">
          {form.getFieldDecorator('userName', {
            rules: [{required: true, message: '请输入至少两个字符的用户名！ ', min: 2}],
            initialValue: formVal.userName,
          })(<Input placeholder="请输入" disabled={action === "update"}/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="密码">
          {form.getFieldDecorator('password', {
            rules: [{required: true, message: '密码长度6-20位！', min: 6, max: 20}],
            initialValue: formVal.password,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
        <FormItem labelCol={{span: 5}} wrapperCol={{span: 15}} label="昵称">
          {form.getFieldDecorator('nickName', {
            rules: [{required: true, message: '请输入至少两个字符的昵称！', min: 2}],
            initialValue: formVal.nickName,
          })(<Input placeholder="请输入"/>)}
        </FormItem>
      </Modal>
    );
  }
}

@connect(({sysUser, loading}) => ({
  user: sysUser,
  loading: loading.models.sysUser,
}))
export default class User extends PureComponent {

  state = {
    selectedRows: [],
    modalVisible: false,
    currentPage: 1,
    pageSize: 10,
    action: "save",//save.update
    formVal: {},
  };

  columns = [{
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: '12%',
  }, {
    title: '账户名',
    dataIndex: 'userName',
    key: 'userName',
  }, {
    title: '昵称',
    dataIndex: 'nickName',
    key: 'nickName',
  }];

  handleModalVisible = (flag, action) => {
    const {selectedRows} = this.state;
    if (action === "update") {
      if (selectedRows.length !== 1) {
        message.warn("请选择一条记录！");
        return;
      } else {
        if (selectedRows.length > 0) {
          this.setState({formVal: selectedRows[0]});
        }
      }
    }
    this.setState({
      modalVisible: !!flag,
      action: action ? action : "save",
    });
  };

  handleUpdateModalVisible = (flag, record) => {
    this.setState({
      updateModalVisible: !!flag,
      stepFormValues: record || {},
    });
  };

  handleSave = fields => {
    const {dispatch} = this.props;
    const {currentPage, pageSize, action} = this.state;
    dispatch({
      type: 'sysUser/saveUser',
      payload: {
        ...fields,
        currentPage,
        pageSize,
      },
    });

    message.success('添加成功');
    this.handleModalVisible();
  };

  handleSelectRows = rows => {
    this.setState({
      selectedRows: rows,
    });
  };

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const {dispatch} = this.props;
    const {formValues} = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = {...obj};
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});

    const params = {
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    this.setState({
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
    });

    dispatch({
      type: 'sysUser/fetch',
      payload: params,
    });
  };

  handleMenuClick = e => {
    const {dispatch} = this.props;
    const {selectedRows, currentPage, pageSize} = this.state;

    if (!selectedRows) return;
    switch (e.key) {
      case 'remove':
        dispatch({
          type: 'sysUser/remove',
          payload: {
            selectedRows,
            currentPage,
            pageSize,
          },
          callback: () => {
            this.setState({
              selectedRows: [],
            });
          },
        });
        break;
      default:
        break;
    }
  };

  componentDidMount() {
    const {location} = this.props;
    this.props.dispatch({
      type: 'sysUser/fetch',
    })
  }

  render() {
    let {user: {data}, loading} = this.props;
    if (!data) {
      data = {};
    }
    const {selectedRows, modalVisible, action, formVal} = this.state;
    const parentMethods = {
      handleSave: this.handleSave,
      handleModalVisible: this.handleModalVisible,
    };
    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="remove">删除</Menu.Item>
        {/*<Menu.Item key="approval">批量审批</Menu.Item>*/}
      </Menu>
    );
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListOperator}>
              <Button icon="plus" type="primary" onClick={() => this.handleModalVisible(true, "save")}>
                新建
              </Button>
              <Button icon="edit" type="primary" onClick={() => this.handleModalVisible(true, "update")}>
                更新
              </Button>
              {selectedRows.length > 0 && (
                <span>
                  {/*<Button>批量操作</Button>*/}
                  <Dropdown overlay={menu}>
                    <Button>
                      更多操作 <Icon type="down"/>
                    </Button>
                  </Dropdown>
                </span>
              )}
            </div>
          </div>
          <StandardTable
            selectedRows={selectedRows}
            loading={loading}
            data={data}
            columns={this.columns}
            onSelectRow={this.handleSelectRows}
            onChange={this.handleStandardTableChange}
          />
          <CreateForm {...parentMethods} modalVisible={modalVisible} action={action} values={{...formVal}}/>
        </Card>
      </PageHeaderWrapper>
    )
  }
}
