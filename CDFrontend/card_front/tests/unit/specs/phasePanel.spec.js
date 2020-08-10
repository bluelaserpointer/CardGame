const validateStub = {
  render: () => {},
  data() {
    return {
      listQuery: {
        page: 1,
        limit: 20,
        total: 0,
        sort: '+id'
      },
    }
  },
  methods: {
    getList: () => {}
  }
};

jest.unmock('axios');
import axios from 'axios';
import MockAdapter from "axios-mock-adapter";

import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import PhasePanel from '@/components/table/PhasePanel'
import Element from 'element-ui';

const localVue = createLocalVue();
localVue.use(Element);

describe('PhasePanel.vue', () => {
  const wrapper = shallowMount(PhasePanel,
    {
      localVue,
      stubs: {
        'PhaseEntityPanel': validateStub,
        'PhaseEditPanel': validateStub,
      }
    });

  it('Phase Panel Resolves Rest', () => {
    wrapper.vm.handleClick({$el: {id: 'pane-second'}},{});
  });
});
