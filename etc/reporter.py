import sys
from colorama import init, Fore, Back, Style

init()

def print_u(text, color = None, underline = '='):
  if color is None:
    print(text)
    print(underline * len(text))
  else:
    print(color + text)
    print(color + underline * len(text))

def main(args):
  arg = args[0]
  if arg == 'fail':
    print()
    print_u(':( ERRORS (These are causing your build to fail):', Fore.RED)
    print()
    with open('errors', 'r') as f:
      for line in f:
        print(Fore.RED + line, end='')
    print()
    print_u('END ERRORS', Fore.RED)
    print()
  elif arg == 'pass':
    print()
    print_u(':D NO ERRORS (Good job!)', Fore.GREEN)
    print()

if __name__ == '__main__':
  main(sys.argv[1:])
