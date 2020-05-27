try:
    import Image
except:
    from PIL import Image

import argparse
import hashlib
import hmac
import os
import resources as res
import xtea
import sys


def create_hmac(mac_pass, msg_bytes):
    
    return hmac.new(
        mac_pass, msg_bytes, digestmod=hashlib.sha256).digest()


def check_hmac(mac, data):
    h_mac = hmac.new(args['m'], bytes(data), digestmod=hashlib.sha256).digest()
    print 'HMAC validation: \n%s\n' % str(h_mac == mac)


def hash_128_bit_pass(passwd):
    h = hashlib.sha256()
    h.update(passwd)
    return h.hexdigest()[:16]


def crypt(key, data, iv):
    return xtea.crypt(key, data, iv)


def read_image(image_path):
    if not os.path.exists(image_path):
        raise IOError('File does not exist: %s' % image_path)
    else:
        return Image.open(image_path)


def read_text(text_path):
    if not os.path.exists(text_path):
            raise IOError('File does not exist: %s' % text_path)
    return open(text_path).read()


def encrypt(data_type):
    h_mac = create_hmac(args['m'], bytes(data))

    secret = '%s--:--%s' % (h_mac, data)

    key = hash_128_bit_pass(args['k'])

    iv = os.urandom(8)
    encrypted_secret = crypt(key, secret, iv)

    encrypted_im = res.hide_msg(image, '%s--:--%s--:--%s' % (
        data_type, iv, encrypted_secret))

    encrypted_im.save(args['image'])

    print "successfully encrypted your secret message"
    encrypted_im.show()


if __name__ == '__main__':
    # Add the command-line arguments
    parser = argparse.ArgumentParser(description='Description of your program')
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('-hide', help='encrypt', action='store_true')
    parser.add_argument(
        '-m', metavar='macpasswd', help='macpassword', required=True)
    parser.add_argument('-k', metavar='passwd', help='password', required=True)
    parser.add_argument('data', nargs='?')
    parser.add_argument('image')

    args = vars(parser.parse_args())

    # Check if the required args are supplied
    # If not print user feedback and exit
    if args['data']:
        if args['data'].endswith('png') or args['data'].endswith('jpg'):
            import base64
            data_type = 'image'
            with open(args['data'], "rb") as imageFile:
                data = base64.b64encode(imageFile.read())
        elif args['data'].endswith('txt'):
            data_type = 'text'
            data = read_text(args['data'])
        else:
            print "need secret message either as .txt or .png file"
            sys.exit(0)

    if args['image']:
        image = read_image(args['image'])
    else:
        print "need image to embed data"
        sys.exit(0)

    # encrypt the secret message
    if args['hide']:
        encrypt(data_type)


