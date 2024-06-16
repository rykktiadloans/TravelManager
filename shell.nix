{ pkgs ? import <nixpkgs> { } }:
pkgs.mkShell {
  packages = with pkgs; [
   xorg.libXtst
  ];
  shellHook = ''
    export LD_LIBRARY_PATH="${pkgs.xorg.libXtst}/lib"
  '';
}
