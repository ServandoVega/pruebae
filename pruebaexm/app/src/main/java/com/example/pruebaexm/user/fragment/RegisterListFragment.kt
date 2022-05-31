package com.example.pruebaexm.user.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.activityViewModels
import com.example.pruebaexm.R
import com.example.pruebaexm.databinding.FragmentRegisterListBinding
import com.example.pruebaexm.user.adapter.RegisterAdapter
import com.example.pruebaexm.user.viewmodel.RegisterViewModel

class RegisterListFragment : Fragment() {
    private lateinit var vBind: FragmentRegisterListBinding
    private val viewModel: RegisterViewModel by activityViewModels()
    private lateinit var adapter: RegisterAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBind = FragmentRegisterListBinding.inflate(inflater, container, false)
        initView()
        initObserver()
        return vBind.root

    }

    private fun initView() {
        viewModel.getListRegister()
        adapter = RegisterAdapter()
        vBind.apply {
            rvRegister.adapter = adapter
            btnCreate.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fragment_container, CreateRegisterFragment())
                        .addToBackStack(RegisterListFragment::class.java.simpleName).commit()
                }


            }
        }
    }

    private fun initObserver() {
        viewModel.listRegister.observe(viewLifecycleOwner) {
            Log.e("prueba", it.toString())
            adapter.setList(it)
        }
    }

}