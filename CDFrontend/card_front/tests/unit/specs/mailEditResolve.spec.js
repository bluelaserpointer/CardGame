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
import MailEditPanel from '@/components/article/MailEditPanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);


describe('MailEditPanel.vue', () => {
  const wrapper = shallowMount(MailEditPanel, {
    localVue,
    stubs:{
      'Tinymce': validateStub
    }
  });

  beforeEach(() => {
    wrapper.vm.$nextTick(() => {});
  });

  let mockAdapter = new MockAdapter(axios);
  let spyPost = jest.spyOn(axios, "post");

  it('Mail Edit Panel Resolves created', async () => {
    expect(wrapper.vm.postForm.image_uri).toBe('');
    expect(wrapper.vm.postForm.title).toBe('');
    wrapper.vm.$nextTick(() => {
      expect(wrapper.vm.postForm.content).toBe('');
    })
  });

  it('Mail Edit Panel Resolves submitForm', async () => {
    expect(spyPost).toHaveBeenCalledTimes(0);
    mockAdapter.onAny().reply(200, true);

    await wrapper.vm.submitForm();
  });

  it('Mail Edit Panel Resolves submitForm Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(1);
    })
  });

  it('Mail Edit Panel Resolves submitForm branchLast', async () => {
    wrapper.vm.postForm.image_uri = undefined;
    wrapper.vm.postForm.title = undefined;
    wrapper.vm.postForm.content = undefined;

    mockAdapter.onAny().reply(200, true);

    await wrapper.vm.submitForm();
  });

  it('Mail Edit Panel Resolves submitForm branchLast Result', () => {
    wrapper.vm.$nextTick(() => {
      expect(spyPost).toHaveBeenCalledTimes(4);
    });
  });

});
