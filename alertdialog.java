 // h/t http://www.androidpeople.com/android-alertdialog-example

 AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
 alt_bld.setMessage("Alarum time has arrived")
  .setCancelable(false)
  .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
   public void onClick(DialogInterface dialog, int id) {
    // Action for 'Yes' Button
   }
 })
 .setNegativeButton("Preserve", new DialogInterface.OnClickListener() {
   public void onClick(DialogInterface dialog, int id) {
    //  Action for 'NO' Button
    dialog.cancel();
   }
 });
 AlertDialog alert = alt_bld.create();
 // Title for AlertDialog
 alert.setTitle("Alarum");
 // Icon for AlertDialog
 alert.setIcon(R.drawable.icon);
 alert.show();

