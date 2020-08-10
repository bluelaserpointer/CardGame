let mockData = { result: [{
    ownPlayerItemId: 0,
    userId: 0,
    itemId: 0,
    itemCount: 0,
    accquireDate: '2000-01-01 01:01:01'
  }]};


const validateStub = {
  render: () => {},
  methods: {
    validate: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import PlayerItemPanel from '@/components/table/playerItemPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerItemPanel.vue', () => {
  const wrapper = shallowMount(PlayerItemPanel,
    {
      localVue,
      stubs:{
        'el-form': validateStub
      }
    });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  mockAdapter.onAny().reply(200, mockData);

  it('Startup', async () => {
    await wrapper.vm.getList();
  });

  it('PlayerItem Entity Panel Resolves created',   async () => {
    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('PlayerItem Entity Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onAny().reply(200, true);
    await wrapper.vm.confirmIdentity();
  });

  it('PlayerItem Entity Panel Resolves confirmIdentity result', async () => {
    expect(wrapper.vm.confirmDelete).toBeTruthy();
    expect(spyPost).toHaveBeenCalledTimes(2);
  });

  it('PlayerItem Entity Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    mockAdapter.onAny().reply(200, true);
    await wrapper.vm.deleteData();
  });

  it('PlayerItem Entity Panel Resolves deleteData result', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(spyPost).toHaveBeenCalledTimes(4);
  });

  it('PlayerItem Entity Panel Resolves resetTemp', () => {
    wrapper.vm.temp = null;

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        ownItemId: undefined,
        userId: undefined,
        itemId: undefined,
        itemCount: undefined,
        accquireDate: undefined
      }
    );
  });

  it('PlayerItem Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('PlayerItem Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('PlayerItem Entity Panel Resolves createData', async () => {
    wrapper.vm.panelVisible = true;
    mockAdapter.onAny().reply(200, true);
    wrapper.vm.createData('temp');
    await wrapper.vm.submitCreate();
  });

  it('PlayerItem Entity Panel Resolves createData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(6);
  });

  it('PlayerItem Entity Panel Resolves updateData', async () => {
    wrapper.vm.panelVisible = true;
    wrapper.vm.updateData('temp');
    await wrapper.vm.submitUpdate();
  });

  it('PlayerItem Entity Panel Resolves updateData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(8);
  });

  it('PlayerItem Entity Panel Resolves Rest',  () => {
    wrapper.vm.handleFilter();
    wrapper.vm.sortChange({prop: 'id'});
    wrapper.vm.sortByID('ascending');
  });

});
