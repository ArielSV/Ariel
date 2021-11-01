package com.example.ariel.flows.imagespicker.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ariel.R
import com.example.ariel.base.fragment.FragmentView
import com.example.ariel.databinding.ImagePickerFragmentBinding
import com.example.ariel.flows.imagespicker.views.ImagesItemView
import com.example.ariel.utils.verifyAvailableNetwork
import com.google.firebase.storage.FirebaseStorage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ImagePickerFragment : FragmentView() {

    private val binding by lazy {
        ImagePickerFragmentBinding.inflate(layoutInflater)
    }

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private val listOfUris: MutableList<Uri> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        initRecyclerView()
    }

    private fun setupListener() {
        binding.buttonSelectImages.setOnClickListener {
            if (verifyAvailableNetwork(requireActivity())) {
                checkPermissions()
            } else {
                showError(getString(R.string.internet_error))
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewImages.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            launchGalleryIntent()
        }
    }

    private fun launchGalleryIntent() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EXTERNAL_STORAGE && resultCode == Activity.RESULT_OK) {
            val clipData = data?.clipData
            if (clipData != null) {
                listOfUris.clear()
                for (i in 0 until clipData.itemCount) {
                    listOfUris.add(clipData.getItemAt(i).uri)
                }
                uploadImages()
            } else {
                val imageUri = Uri.parse(data?.data.toString())
                listOfUris.add(imageUri)
                uploadImages()
            }
        }
    }

    private fun uploadImages() {
        showProgressDialog()
        listOfUris.map {
            val fileName = System.currentTimeMillis()
            val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
            storageReference.putFile(it).addOnSuccessListener {
                dismissProgressDialog()
                showMessage(getString(R.string.images_upload_complete))
            }.addOnFailureListener { error ->
                dismissProgressDialog()
                showError(error.message.orEmpty())
            }
        }
        showSelectedImages()
    }

    private fun showSelectedImages() {
        groupAdapter.addAll(
            listOfUris.map {
                createItem(it)
            }
        )
        listOfUris.clear()
    }

    private fun createItem(uri: Uri?): ImagesItemView {
        return ImagesItemView(uri)
    }

    private companion object {
        const val REQUEST_EXTERNAL_STORAGE = 100
    }
}