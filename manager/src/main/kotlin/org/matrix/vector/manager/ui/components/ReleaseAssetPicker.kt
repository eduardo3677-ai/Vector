package org.matrix.vector.manager.ui.components

import android.text.format.Formatter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.matrix.vector.manager.R
import org.matrix.vector.manager.data.model.ReleaseAsset
import org.matrix.vector.manager.ui.theme.LocalizedOverlay

/** Lets the reader choose the exact APK a release publishes before it is installed. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseAssetPicker(
    assets: List<ReleaseAsset>,
    onDismiss: () -> Unit,
    onPick: (ReleaseAsset) -> Unit,
) {
    val context = LocalContext.current
    ModalBottomSheet(onDismissRequest = onDismiss) {
        LocalizedOverlay {
            Column {
                SheetHeading(stringResource(R.string.store_choose_asset), Icons.Rounded.Download)
                assets.forEach { asset ->
                    ListItem(
                        modifier = Modifier.clickable { onPick(asset) },
                        supportingContent = {
                            val size = Formatter.formatShortFileSize(context, asset.size)
                            val downloads =
                                asset.downloadCount?.let {
                                    context.resources.getQuantityString(
                                        R.plurals.store_asset_downloads,
                                        it,
                                        it,
                                    )
                                }
                            Text(
                                text = listOfNotNull(size, downloads).joinToString(" / "),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        },
                        colors = sheetRowColors,
                    ) {
                        Text(asset.name.orEmpty())
                    }
                }
                Spacer(Modifier.navigationBarsPadding().height(16.dp))
            }
        }
    }
}
