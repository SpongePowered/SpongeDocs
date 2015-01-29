import sys
from colorama import init, Fore, Back, Style

init()

def print_u(text, underline = '='):
  print(text)
  print(underline * len(text))

def main(args):
  arg = args[0]
  if arg == 'fail':
    print(Fore.RED)
    print_u(':( ERRORS (These are causing your build to fail):')
    print()
    print(open('warnings', 'r').read())
    print_u('END ERRORS')
  elif arg == 'pass':
    print(Fore.GREEN)
    print_u(':D NO ERRORS (Good job!)')
    print()

if __name__ == '__main__':
  main(sys.argv[1:])
