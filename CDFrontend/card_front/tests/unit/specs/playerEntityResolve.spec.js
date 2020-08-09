let mockData = { result: [{
    userId: 1,
    userName: '1',
    password: '1',
    phoneNumber: '1',
    email: '1',
    credits: 1,
    access: true,
    level: 1,
    curExpPoint: 1,
    stamina: 1,
    money: 1,
    grade: 1.0,
    engKnowledge: 1,
    mathKnowledge: 1,
    chiKnowledge: 1
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
import PlayerEntityPanel from '@/components/table/playerEntityPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('PlayerEntityPanel.vue', () => {
  const wrapper = shallowMount(PlayerEntityPanel,
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

  it('Player Entity Panel Resolves created',   async () => {
    expect(spyPost).toHaveBeenCalledTimes(1);
  });

  it('Player Entity Panel Resolves confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onAny().reply(200, true);
    await wrapper.vm.confirmIdentity();
  });

  it('Player Entity Panel Resolves confirmIdentity result', async () => {
    expect(wrapper.vm.confirmDelete).toBeTruthy();
    expect(spyPost).toHaveBeenCalledTimes(2);
  });

  it('Player Entity Panel Resolves deleteData',  async () => {
    wrapper.vm.confirmDelete = true;
    mockAdapter.onAny().reply(200, true);
    await wrapper.vm.deleteData();
  });

  it('Player Entity Panel Resolves deleteData result', async () => {
    expect(wrapper.vm.panelVisible).toBeFalsy();
    expect(wrapper.vm.deleteVisible).toBeFalsy();
    expect(spyPost).toHaveBeenCalledTimes(4);
  });

  it('Player Entity Panel Resolves resetTemp', () => {
    wrapper.vm.temp = null;

    wrapper.vm.resetTemp();
    expect(wrapper.vm.temp).toStrictEqual(
      {
        userId: undefined,
        userName: '',
        password: '',
        phoneNumber: '',
        email: '',
        credits: 0,
        access: true,
        level: 0,
        curExpPoint: 0,
        stamina: 0,
        money: 0,
        grade: 0.0,
        engKnowledge: 0,
        mathKnowledge: 0,
        chiKnowledge: 0,
        identity: 'ROLE_USER'
      }
    );
  });

  it('Player Entity Panel Resolves handleCreate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleCreate();
    expect(wrapper.vm.dialogStatus).toBe('create');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Entity Panel Resolves handleUpdate', () => {
    wrapper.vm.dialogStatus = 'xxx';
    wrapper.vm.panelVisible = false;

    wrapper.vm.handleUpdate();
    expect(wrapper.vm.dialogStatus).toBe('update');
    expect(wrapper.vm.dialogStatus).toBeTruthy();
  });

  it('Player Entity Panel Resolves createData', async () => {
    wrapper.vm.panelVisible = true;
    mockAdapter.onAny().reply(200, true);
    wrapper.vm.createData('temp');
    await wrapper.vm.submitCreate();
  });

  it('Player Entity Panel Resolves createData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(6);
  });

  it('Player Entity Panel Resolves updateData', async () => {
    wrapper.vm.panelVisible = true;
    wrapper.vm.updateData('temp');
    await wrapper.vm.submitUpdate();
  });

  it('Player Entity Panel Resolves updateData result',  () => {
    expect(spyPost).toHaveBeenCalledTimes(8);
  });
});
