import React from 'react';
import {Drawer} from 'antd';
import SiderMenu from './SiderMenu';
import BaseMenu, {getMenuMatches} from './BaseMenu';
import {urlToList} from '../_utils/pathTools';

/**
 * Recursively flatten the data
 * [{path:string},{path:string}] => {path,path2}
 * @param  menus
 */
const getFlatMenuKeys = menuData => {
  let keys = [];
  menuData.forEach(item => {
    if (item.children) {
      keys = keys.concat(getFlatMenuKeys(item.children));
    }
    keys.push(item.path);
  });
  return keys;
};

/**
 * 获得菜单子节点
 * @memberof SiderMenu
 */
const getDefaultCollapsedSubMenus = props => {
  const {
    location: {pathname},
    menuData
  } = props;
  const flatMenuKeys = getFlatMenuKeys(menuData);
  if (!flatMenuKeys) {
    return [];
  }
  return urlToList(pathname)
    .map(item => getMenuMatches(flatMenuKeys, item)[0])
    .filter(item => item);
};

const SiderMenuWrapper = props => {
  const {isMobile, menuData, collapsed, onCollapse} = props;
  // const openKeys = getDefaultCollapsedSubMenus(props);
  // const defaultProps = !collapsed && menuData.length > 0 ? {selectedKeys: openKeys} : {};
  const defaultProps={};
  return isMobile ? (
    <Drawer
      visible={!collapsed}
      placement="left"
      onClose={() => onCollapse(true)}
      style={{
        padding: 0,
        height: '100vh',
      }}
    >
      <SiderMenu
        {...props}
        flatMenuKeys={getFlatMenuKeys(menuData)}
        collapsed={isMobile ? false : collapsed}
      />
    </Drawer>
  ) : (
    <SiderMenu {...props} flatMenuKeys={getFlatMenuKeys(menuData)} {...defaultProps}/>
  );
};

export default SiderMenuWrapper;
