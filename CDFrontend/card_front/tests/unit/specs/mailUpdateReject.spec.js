const validateStub = {
  render: () => {},
  methods: {
    setContent: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";


import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import MailUpdatePanel from '@/components/article/MailUpdatePanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);


describe('MailUpdatePanel.vue', () => {
  const wrapper = shallowMount(MailUpdatePanel, {
    localVue,
    stubs:{
      'Tinymce': validateStub
    },
    propsData:{
      updateContent: {
        mail: 0,
        mailImg : '0',
        mailName : '0',
        mailDescription : '0',
      }
    }
  });

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {});
  });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  it('Mail Update Panel Rejects submitForm', async () => {
    expect(spyPost).toHaveBeenCalledTimes(0);
    mockAdapter.onAny().reply(400, true);

    await wrapper.vm.submitForm();
  });

  it('Mail Update Panel Rejects submitForm Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1);
    })
  });

  it('Mail Entity Panel Rejects confirmIdentity', async () => {
    wrapper.vm.confirmDelete = false;
    mockAdapter.onAny().reply(400, true);

    await wrapper.vm.confirmIdentity();
  });

  it('Mail Entity Panel Rejects confirmIdentity Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.confirmDelete).toBeFalsy();
      expect(spyPost).toHaveBeenCalledTimes(2);
    });
  });

  it('Mail Entity Panel Rejects deleteData',  async () => {
    wrapper.vm.confirmDelete = true;

    mockAdapter.onAny().reply(400, true);

    await wrapper.vm.deleteData();
  });

  it('Mail Entity Panel Rejects deleteData Result',  () => {
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.panelVisible).toBeTruthy();
      expect(wrapper.vm.deleteVisible).toBeTruthy();
      expect(spyPost).toHaveBeenCalledTimes(3);
    });
  });


  it('Mail Update Panel Rejects submitForm branchLast', async () => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    mockAdapter.onAny().reply(400, true);

    await wrapper.vm.submitForm();
  });

  it('Mail Update Panel Rejects submitForm branchLast Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(4);
    });
  });

});
